package com.mcs.analyser.fuel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FuelRepository extends JpaRepository<FuelDataPoint, Long> {
    List<FuelDataPoint> findByMcsSystemID(Integer mcsSystemID);
    List<FuelDataPoint> findByMcsSystemIDAndTimeSentBetween(Integer mcsSystemID, LocalDateTime timeSentStart, LocalDateTime timeSentEnd);
}
