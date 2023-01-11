package com.mcs.analyser.production;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProductionControllerTest {
    int serverPort = 17000;

    TestRestTemplate restTemplate = new TestRestTemplate();

    String baseURL = String.format("http://localhost:%d/production", serverPort);

    @Autowired
    ProductionRepository productionRepository;

    @Test
    public void getProductionDataPointsWithinTimeSpan_onlyDataPointsInTimeSpan_thenOK() {
        //Arrange
        Integer mcsSystemID = 4;
        Integer amountProduced = 4000;
        String itemName = "minecraft:kelp";
        Integer harvestIntervalAsSeconds = 300;

        LocalDateTime timeSent = LocalDateTime.now();
        Integer timeSpanAsMinutes = 8;
        URI url = URI.create(String.format(baseURL + "/get-by-system-id?mcssystemid=%d&accumulated=false&datatimespan=%d&interval=0", mcsSystemID, timeSpanAsMinutes));

        List<ProductionDataPoint> productionDataPoints = Arrays.asList(
            new ProductionDataPoint(1L, mcsSystemID, timeSent.minusMinutes(10), amountProduced, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(2L, mcsSystemID, timeSent.minusMinutes(7), amountProduced, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(3L, mcsSystemID, timeSent.minusMinutes(4), amountProduced, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(4L, mcsSystemID, timeSent.minusMinutes(1), amountProduced, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(5L, mcsSystemID, timeSent.plusMinutes(2), amountProduced, itemName, harvestIntervalAsSeconds)
        );
        productionRepository.saveAll(productionDataPoints);

        ProductionDataPoint[] expectedArr = {
            productionDataPoints.get(1),
            productionDataPoints.get(2),
            productionDataPoints.get(3),
            productionDataPoints.get(4)
        };

        ParameterizedTypeReference<HashMap<String, List<ProductionDataPoint>>> responseType =
            new ParameterizedTypeReference<>(){};

        //Act
        Map<String, List<ProductionDataPoint>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();

        //Assert
        assert response != null;
        Assertions.assertArrayEquals(expectedArr, response.get(itemName).toArray());
    }

    @Test
    public void getProductionDataPoints_isAccumulated_thenOK() {
        //Arrange
        Integer mcsSystemID = 4;
        String itemName = "minecraft:kelp";
        Integer harvestIntervalAsSeconds = 300;
        LocalDateTime timeSent = LocalDateTime.now();

        URI url = URI.create(String.format(baseURL + "/get-by-system-id?mcssystemid=%d&accumulated=true&interval=0", mcsSystemID));

        List<ProductionDataPoint> productionDataPoints = Arrays.asList(
            new ProductionDataPoint(1L, mcsSystemID, timeSent.minusMinutes(5), 1000, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(2L, mcsSystemID, timeSent.minusMinutes(4), 500, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(3L, mcsSystemID, timeSent.minusMinutes(3), 3000, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(4L, mcsSystemID, timeSent.minusMinutes(2), -2000, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(5L, mcsSystemID, timeSent.minusMinutes(1), 500, itemName, harvestIntervalAsSeconds)
        );
        productionRepository.saveAll(productionDataPoints);

        ProductionDataPoint[] expectedArr = {
            new ProductionDataPoint(1L, mcsSystemID, timeSent.minusMinutes(5), 1000, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(2L, mcsSystemID, timeSent.minusMinutes(4), 1500, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(3L, mcsSystemID, timeSent.minusMinutes(3), 4500, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(4L, mcsSystemID, timeSent.minusMinutes(2), 2500, itemName, harvestIntervalAsSeconds),
            new ProductionDataPoint(5L, mcsSystemID, timeSent.minusMinutes(1), 3000, itemName, harvestIntervalAsSeconds)
        };

        ParameterizedTypeReference<HashMap<String, List<ProductionDataPoint>>> responseType =
            new ParameterizedTypeReference<>(){};

        //Act
        Map<String, List<ProductionDataPoint>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();

        //Assert
        Assertions.assertArrayEquals(expectedArr, response.get(itemName).toArray());
    }

    @Test
    public void getProductionDataPoints_isSortedByTimeSent_thenOK() {
        //Arrange
        Integer mcsSystemID = 4;
        Integer amountProduced = 4000;
        String itemName = "minecraft:kelp";
        Integer harvestIntervalAsSeconds = 300;
        LocalDateTime timeSent = LocalDateTime.now();

        URI url = URI.create(String.format(baseURL + "/get-by-system-id?mcssystemid=%d&accumulated=false&interval=0", mcsSystemID));

        List<ProductionDataPoint> productionDataPoints = Arrays.asList(
                new ProductionDataPoint(1L, mcsSystemID, timeSent.minusMinutes(4), amountProduced, itemName, harvestIntervalAsSeconds),
                new ProductionDataPoint(2L, mcsSystemID, timeSent.minusMinutes(2), amountProduced, itemName, harvestIntervalAsSeconds),
                new ProductionDataPoint(3L, mcsSystemID, timeSent.minusMinutes(1), amountProduced, itemName, harvestIntervalAsSeconds),
                new ProductionDataPoint(4L, mcsSystemID, timeSent.minusMinutes(5), amountProduced, itemName, harvestIntervalAsSeconds),
                new ProductionDataPoint(5L, mcsSystemID, timeSent.minusMinutes(3), amountProduced, itemName, harvestIntervalAsSeconds)
        );
        productionRepository.saveAll(productionDataPoints);

        ProductionDataPoint[] expectedArr = {
                new ProductionDataPoint(4L, mcsSystemID, timeSent.minusMinutes(5), amountProduced, itemName, harvestIntervalAsSeconds),
                new ProductionDataPoint(1L, mcsSystemID, timeSent.minusMinutes(4), amountProduced, itemName, harvestIntervalAsSeconds),
                new ProductionDataPoint(5L, mcsSystemID, timeSent.minusMinutes(3), amountProduced, itemName, harvestIntervalAsSeconds),
                new ProductionDataPoint(2L, mcsSystemID, timeSent.minusMinutes(2), amountProduced, itemName, harvestIntervalAsSeconds),
                new ProductionDataPoint(3L, mcsSystemID, timeSent.minusMinutes(1), amountProduced, itemName, harvestIntervalAsSeconds)
        };

        ParameterizedTypeReference<HashMap<String, List<ProductionDataPoint>>> responseType =
                new ParameterizedTypeReference<>(){};

        //Act
        Map<String, List<ProductionDataPoint>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();

        //Assert
        assert response != null;
        Assertions.assertArrayEquals(expectedArr, response.get(itemName).toArray());
    }
}