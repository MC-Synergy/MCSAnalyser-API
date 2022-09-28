package com.mcs.analyser.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionService {
    private final ProductionRepository productionRepository;

    @Autowired
    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    public List<ProductionDataPoint> getProductionDataPoints(Integer mcsSystemID){
        return productionRepository.findByMcsSystemID(mcsSystemID);
    }

    public void saveProductionDataPoint(ProductionDataPoint productionDataPoint){
        productionRepository.save(productionDataPoint);
    }
}
