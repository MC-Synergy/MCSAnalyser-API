package com.mcs.analyser.common;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class DataPoint {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_generator")
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name="mcs_system_id",
            nullable = false
    )
    protected Integer mcsSystemID;

    @Column(
            name="time_sent",
            nullable = false
    )
    protected LocalDateTime timeSent;

    public Long getId() {
        return id;
    }

    public Integer getMcsSystemID() {
        return mcsSystemID;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

}
