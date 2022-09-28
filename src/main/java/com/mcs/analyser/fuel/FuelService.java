package com.mcs.analyser.fuel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FuelService {
    private final FuelRepository fuelRepository;

    @Autowired
    public FuelService(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    public List<FuelDataPoint> getFuelDataPoints(Integer mcsSystemID) {
        return fuelRepository.findByMcsSystemID(mcsSystemID);
    }

    public List<FuelDataPoint> getFuelDataPointsBetween(Integer mcsSystemID, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return fuelRepository.findByMcsSystemIDAndTimeSentBetween(mcsSystemID, dateStart, dateEnd);
    }

    public void saveFuelDataPoint(FuelDataPoint fuelDataPoint){
        fuelRepository.save(fuelDataPoint);
    }
}
