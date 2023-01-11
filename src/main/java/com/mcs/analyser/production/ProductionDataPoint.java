package com.mcs.analyser.production;

import com.mcs.analyser.common.DataPoint;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "product_data_point")
@SequenceGenerator(
        name="id_generator",
        sequenceName="product_data_point_sequence",
        allocationSize=1
)
@EqualsAndHashCode
public class ProductionDataPoint extends DataPoint {

    @Column(
            name = "items_produced",
            nullable = false
    )
    private Integer amountProduced;

    @Column(
            name = "item_name",
            nullable = false
    )
    private String itemName;

    @Column(
            name = "harvest_interval_as_seconds",
            nullable = false
    )
    private Integer harvestIntervalAsSeconds;

    public ProductionDataPoint(Long id, Integer mcsSystemID, LocalDateTime timeSent, Integer amountProduced, String itemName, Integer harvestIntervalAsSeconds) {
        super(id, mcsSystemID, timeSent);
        this.amountProduced = amountProduced;
        this.itemName = itemName;
        this.harvestIntervalAsSeconds = harvestIntervalAsSeconds;
    }

    protected ProductionDataPoint() {}

    public Integer getAmountProduced() {
        return amountProduced;
    }

    public void setAmountProduced(Integer amountProduced) {
        this.amountProduced = amountProduced;
    }

    public String getItemName() {
        return itemName;
    }
    public Integer getHarvestIntervalAsSeconds() {
        return harvestIntervalAsSeconds;
    }
}
