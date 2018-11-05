package com.iisereb.sensorms.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
public class MonitoringEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long monitoringId;

    @OneToOne(
        targetEntity = SensorEntity.class,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "sensor", referencedColumnName = "sensorId")
    private SensorEntity sensor;
    private double value;

}
