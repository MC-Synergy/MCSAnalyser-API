package com.mcs.analyser.production;

import com.mcs.analyser.common.DataPoint;
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

    public Map<String, List<ProductionDataPoint>> getProductionDataPoints(int mcsSystemID, boolean accumulated, int intervalAsMinutes){
        List<ProductionDataPoint> productionDataPoints = productionRepository.findByMcsSystemID(mcsSystemID);
        Map<String, List<ProductionDataPoint>> producedItems = createProducedItemMap(productionDataPoints);

        if (accumulated) {
            producedItems.replaceAll((key, PDPs) -> accumulateAmountProduced(PDPs));
        }
        else {
            producedItems.replaceAll((key, PDPs) -> calculateAverages(PDPs, intervalAsMinutes));
        }

        producedItems.replaceAll((key, PDPs) -> accumulateAmountProduced(PDPs));
        return producedItems;
    }

    public Map<String, List<ProductionDataPoint>> getProductionDataPoints(int mcsSystemID, boolean accumulated, int timeSpanAsMinutes, int intervalAsMinutes){
        LocalDateTime startDate = LocalDateTime.now();
        startDate = startDate.minusMinutes(timeSpanAsMinutes);
        List<ProductionDataPoint> productionDataPoints = productionRepository.findByMcsSystemIDAndTimeSentGreaterThanEqual(mcsSystemID, startDate);
        Map<String, List<ProductionDataPoint>> producedItems = createProducedItemMap(productionDataPoints);

        if (accumulated) {
            producedItems.replaceAll((key, PDPs) -> accumulateAmountProduced(PDPs));
        }
        else {
            producedItems.replaceAll((key, PDPs) -> calculateAverages(PDPs, intervalAsMinutes));
        }

        return producedItems;
    }

    private List<ProductionDataPoint> calculateAverages(List<ProductionDataPoint> productionDataPoints, int intervalAsMinutes) {
        if (productionDataPoints.size() < 1 || intervalAsMinutes == 0) {
            return productionDataPoints;
        }
        productionDataPoints.sort(Comparator.comparing(DataPoint::getTimeSent));

        List<ProductionDataPoint> calculatedList = new ArrayList<>();
        int totalIntervals = 0;
        int totalPDPsInInterval = 0;
        int amountProducedInInterval = 0;

        LocalDateTime timeSentOfFirstPDP = productionDataPoints.get(0).getTimeSent();
        ProductionDataPoint lastPDPOfInterval = productionDataPoints.get(0);

        for (int i = 0; i < productionDataPoints.size(); i++) {
            ProductionDataPoint pdp = productionDataPoints.get(i);
            LocalDateTime timeBarrier = timeSentOfFirstPDP.plusMinutes((long) intervalAsMinutes * totalIntervals);
            if (pdp.getTimeSent().isBefore(timeBarrier) || pdp.getTimeSent().isEqual(timeBarrier)) {
                totalPDPsInInterval++;
                amountProducedInInterval += pdp.getAmountProduced();
                lastPDPOfInterval = pdp;
            }
            else {
                int newAverageAmountProduced = (int)Math.round((double)amountProducedInInterval / (double)totalPDPsInInterval);
                lastPDPOfInterval.setAmountProduced(newAverageAmountProduced);
                calculatedList.add(lastPDPOfInterval);
                amountProducedInInterval = 0;
                totalPDPsInInterval = 0;
                totalIntervals += 1;
                i -=1;
            }
        }
        return calculatedList;
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
