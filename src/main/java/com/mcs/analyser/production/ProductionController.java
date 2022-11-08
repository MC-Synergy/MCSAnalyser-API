package com.mcs.analyser.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/production")
public class ProductionController {
    private final ProductionService productionService;

    @Autowired
    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello from Docker!";
    }
    @GetMapping(
            value = "/get-by-system-id",
            params = {"mcssystemid", "accumulated"}
    )
    public Map<String, List<ProductionDataPoint>> getProductionDataPoints(
            @RequestParam(name = "mcssystemid") int mcsSystemID,
            @RequestParam(name = "accumulated") boolean accumulated,
            @RequestParam(name = "interval") int intervalAsMinutes
    ){
        return productionService.getProductionDataPoints(mcsSystemID, accumulated, intervalAsMinutes);
    }

    @GetMapping(
            value = "/get-by-system-id",
            params = {"mcssystemid", "accumulated", "datatimespan"}
    )
    public Map<String, List<ProductionDataPoint>> getProductionDataPointsWithinTimeSpan(
            @RequestParam(name = "mcssystemid") int mcsSystemID,
            @RequestParam(name = "accumulated") boolean accumulated,
            @RequestParam(name = "datatimespan") int timeSpanInMinutes,
            @RequestParam(name = "interval") int intervalAsMinutes
    ){
        return productionService.getProductionDataPoints(mcsSystemID, accumulated, timeSpanInMinutes, intervalAsMinutes);
    }

    @PostMapping("/post")
    public void postProductionDataPoint(@RequestBody ArrayList<ProductionDataPoint> productionDataPoints, HttpServletRequest request){
        System.out.println(request.getRequestURL());
        for (ProductionDataPoint pdp : productionDataPoints) {
            productionService.saveProductionDataPoint(pdp);
        }
    }
}
