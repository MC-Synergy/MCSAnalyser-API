package com.mcs.analyser.fuel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuelService {
    private final FuelRepository fuelRepository;

    @Autowired
    public FuelService(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    public void AddFuelDataPoint(FuelDataPoint fuelDataPoint){
        fuelRepository.save(fuelDataPoint);
    }
}
