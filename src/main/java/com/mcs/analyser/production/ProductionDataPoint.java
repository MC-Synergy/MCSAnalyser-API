package com.mcs.analyser.production;

import com.mcs.analyser.common.DataPoint;

import javax.persistence.*;

@Entity(name = "product_data_point")
@SequenceGenerator(
        name="id_generator",
        sequenceName="product_data_point_sequence",
        allocationSize=1
)
public class ProductionDataPoint extends DataPoint {

    @Column(
            name = "items_produced",
            nullable = false
    )
    private Integer amountProduced;

    @Column(
            name = "timespan_as_seconds",
            nullable = false
    )
    private Integer timeSpanAsSeconds;

    @Column(
            name = "item_name",
            nullable = false
    )
    private String itemName;

    protected ProductionDataPoint() {}

    public Integer getAmountProduced() {
        return amountProduced;
    }

    public Integer getTimeSpanAsSeconds() {
        return timeSpanAsSeconds;
    }

    public String getItemName() {
        return itemName;
    }
}
