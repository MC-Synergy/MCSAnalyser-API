package com.mcs.analyser.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductionService {
    private final ProductionRepository productionRepository;

    @Autowired
    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    public Map<String, List<ProductionDataPoint>> getProductionDataPoints(Integer mcsSystemID){
        List<ProductionDataPoint> productionDataPoints = productionRepository.findByMcsSystemID(mcsSystemID);

        Map<String, List<ProductionDataPoint>> producedItems = new HashMap<String, List<ProductionDataPoint>>();
        for (ProductionDataPoint pdp : productionDataPoints) {
            producedItems.computeIfAbsent(pdp.getItemName(), k -> new ArrayList<>()).add(pdp);
        }
        return producedItems;
    }

    public void saveProductionDataPoint(ProductionDataPoint productionDataPoint){
        productionRepository.save(productionDataPoint);
    }
}
