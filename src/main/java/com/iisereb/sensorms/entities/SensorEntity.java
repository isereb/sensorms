package com.iisereb.sensorms.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@ToString
@EqualsAndHashCode
public class SensorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sensorId;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(name = "owner", referencedColumnName = "userId")
    private UserEntity owner;

    @Size(max = 255, message = "Description cannot be longer than 200 symbols")
    private String description;
    private double maxThreshold;
    private double minThreshold;

    @Enumerated(value = EnumType.STRING)
    private UnitType unitType = UnitType.CELSIUS;

    @OneToMany(
        targetEntity = MonitoringEntity.class,
        fetch = FetchType.EAGER,
        mappedBy = "sensor"
    )
    private List<MonitoringEntity> monitoringEntityList;


}
