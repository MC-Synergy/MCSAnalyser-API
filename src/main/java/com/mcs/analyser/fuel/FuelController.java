package com.mcs.analyser.fuel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/fuel")
public class FuelController {
    private final FuelService fuelService;

    @Autowired
    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @GetMapping("/get-by-system-id")
    public List<FuelDataPoint> getFuelDataPoints(@RequestParam(name = "mcssystemid") int mcsSystemID){
        return fuelService.getFuelDataPoints(mcsSystemID);
    }

    @GetMapping("/get-by-system-id-between")
    public List<FuelDataPoint> getFuelDataPoints(
            @RequestParam(name = "mcssystemid") int mcsSystemID,
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart,
            @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd
            ){
        return fuelService.getFuelDataPointsBetween(
                mcsSystemID,
                dateStart,
                dateEnd
        );
    }

    @PostMapping("/post")
    public void postFuelDataPoint(@RequestBody FuelDataPoint fuelDataPoint){
        System.out.println("Received FDP");
        fuelService.saveFuelDataPoint(fuelDataPoint);
    }
}
