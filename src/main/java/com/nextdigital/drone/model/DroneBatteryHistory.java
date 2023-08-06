package com.nextdigital.drone.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "drone_battery_history")
public class DroneBatteryHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drone_SEQ_GEN")
    @SequenceGenerator(name = "drone_SEQ_GEN", sequenceName = "drone_SEQ", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "created_date")
    private Date createdDate;

    @JoinColumn(name = "drone_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Drone drone;

    @Column(name = "battery_capacity", columnDefinition = "float4 default 0.0")
    @Max(value = 100, message = "The maximum battery capacity is 100")
    private Float batteryCapacity;

    public DroneBatteryHistory(Drone drone, Float batteryCapacity) {
        this.drone = drone;
        this.batteryCapacity = batteryCapacity;
    }
}
