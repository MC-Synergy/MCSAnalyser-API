package com.mcs.analyser.production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductionRepository extends JpaRepository<ProductionDataPoint, Long> {
    List<ProductionDataPoint> findByMcsSystemID(Integer mcsSystemID);
    List<ProductionDataPoint> findByMcsSystemIDAndTimeSentGreaterThanEqual(Integer mcsSystemID, LocalDateTime date);
}
