package com.mcs.analyser.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductionService {
    private final ProductionRepository productionRepository;

    @Autowired
    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    public Map<String, List<ProductionDataPoint>> getProductionDataPoints(int mcsSystemID, boolean accumulated){
        List<ProductionDataPoint> productionDataPoints = productionRepository.findByMcsSystemID(mcsSystemID);
        Map<String, List<ProductionDataPoint>> producedItems = createProducedItemMap(productionDataPoints);

        if (!accumulated) {
            return producedItems;
        }

        producedItems.replaceAll((key, value) -> accumulateAmountProduced(value));
        return producedItems;
    }

    public Map<String, List<ProductionDataPoint>> getProductionDataPoints(int mcsSystemID, boolean accumulated, int timeSpanAsMinutes){
        //TODO Remove this line in production and make it LocalDateTime.now()
        LocalDateTime startDate = LocalDateTime.of(2022, 10, 1, 14, 18, 1);
        startDate = startDate.minusMinutes(timeSpanAsMinutes);
        List<ProductionDataPoint> productionDataPoints = productionRepository.findByMcsSystemIDAndTimeSentGreaterThanEqual(mcsSystemID, startDate);
        Map<String, List<ProductionDataPoint>> producedItems = createProducedItemMap(productionDataPoints);

        if (!accumulated) {
            return producedItems;
        }

        producedItems.replaceAll((key, value) -> accumulateAmountProduced(value));
        return producedItems;
    }



    private Map<String, List<ProductionDataPoint>> createProducedItemMap(List<ProductionDataPoint> productionDataPoints) {
        Map<String, List<ProductionDataPoint>> producedItems = new HashMap<>();
        for (ProductionDataPoint pdp : productionDataPoints) {
            producedItems.computeIfAbsent(pdp.getItemName(), k -> new ArrayList<>()).add(pdp);
        }
        return producedItems;
    }

    private List<ProductionDataPoint> accumulateAmountProduced(List<ProductionDataPoint> productionDataPoints){
        int lastAmountProduced = 0;
        for (ProductionDataPoint pdp : productionDataPoints) {
            pdp.setAmountProduced(pdp.getAmountProduced() + lastAmountProduced);
            lastAmountProduced = pdp.getAmountProduced();
        }
        return productionDataPoints;
    }

    public void saveProductionDataPoint(ProductionDataPoint productionDataPoint){
        productionRepository.save(productionDataPoint);
      }
}
