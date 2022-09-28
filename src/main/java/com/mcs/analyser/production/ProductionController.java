package com.mcs.analyser.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/production")
public class ProductionController {
    private final ProductionService productionService;

    @Autowired
    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/get-by-system-id")
    public List<ProductionDataPoint> getProductionDataPoints(@RequestParam(name = "mcssystemid") int mcsSystemID){
        return productionService.getProductionDataPoints(mcsSystemID);
    }

    @PostMapping("/post")
    public void postProductionDataPoint(@RequestBody ProductionDataPoint productionDataPoint){
        productionService.saveProductionDataPoint(productionDataPoint);
    }
}
