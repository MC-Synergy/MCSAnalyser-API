package com.mcs.analyser.fuel;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity(name = "fuel_data_point")
@Table
public class FuelDataPoint {
    @Id
    @SequenceGenerator(
            name = "fuel_data_point_sequence",
            sequenceName = "fuel_data_point_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator =  "fuel_data_point_sequence"
    )
    @Column(
            name="id",
            updatable = false
    )
    private Long Id;

    @Column(
            name="fuel_count",
            nullable = false
    )
    private int FuelCount;

    @Column(
            name="mcs_system_id",
            nullable = false
    )
    private int MCSSystemID;

    @Column(
            name="time_received",
            nullable = false
    )
    private LocalDateTime TimeReceived;

    @Column(
            name="sent_before_round",
            nullable = false
    )
    private Boolean SentBeforeRound;

    protected FuelDataPoint() {}

    protected FuelDataPoint(int fuelCount, int MCSSystemID, LocalDateTime timeReceived, boolean sentBeforeRound) {
        FuelCount = fuelCount;
        this.MCSSystemID = MCSSystemID;
        TimeReceived = timeReceived;
        SentBeforeRound = sentBeforeRound;
    }
}
