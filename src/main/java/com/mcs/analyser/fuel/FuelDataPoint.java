package com.mcs.analyser.fuel;


import com.mcs.analyser.common.DataPoint;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "fuel_data_point")
@SequenceGenerator(
        name = "id_generator",
        sequenceName = "fuel_data_point_sequence",
        allocationSize = 1
)
public class FuelDataPoint extends DataPoint {
    @Column(
            name="fuel_used",
            nullable = false
    )
    private Integer fuelUsed;

    @Column(
            name="turtles_in_system",
            nullable = false
    )
    private Integer turtlesInSystem;

    protected FuelDataPoint() {}

    public Integer getFuelUsed() {
        return fuelUsed;
    }

    public Integer getTurtlesInSystem() {
        return turtlesInSystem;
    }
}
