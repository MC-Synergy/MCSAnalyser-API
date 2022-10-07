package com.mcs.analyser.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/production")
@CrossOrigin
public class ProductionController {
    private final ProductionService productionService;

    @Autowired
    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello!";
    }
    @GetMapping("/get-by-system-id")
    public Map<String, List<ProductionDataPoint>> getProductionDataPoints(@RequestParam(name = "mcssystemid") int mcsSystemID){
        return productionService.getProductionDataPoints(mcsSystemID);
    }

    @PostMapping("/post")
    public void postProductionDataPoint(@RequestBody ArrayList<ProductionDataPoint> productionDataPoints){
        System.out.println("received!!");
        for (ProductionDataPoint pdp : productionDataPoints) {
            productionService.saveProductionDataPoint(pdp);
        }
    }
}
