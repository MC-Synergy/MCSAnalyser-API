package com.mcs.analyser.fuel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
public class FuelController {
    private final FuelService fuelService;

    @Autowired
    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @GetMapping("/Get")
    public ArrayList<FuelDataPoint> getFuelDataPoints(){
        //TODO
        return new ArrayList<>();
    }

    @PostMapping("/Post")
    public void postFuelDataPoint(@RequestParam int fuelCount){
        FuelDataPoint testPoint = new FuelDataPoint(fuelCount, 2, LocalDateTime.now(), true);
        fuelService.AddFuelDataPoint(testPoint);
    }
}
