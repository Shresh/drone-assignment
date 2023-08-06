package com.nextdigital.drone.model;

import com.nextdigital.drone.enums.Model;
import com.nextdigital.drone.enums.State;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "drone",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"serial_number"}, name = "unique_drone_serial_number"),
        }
)
public class Drone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drone_SEQ_GEN")
    @SequenceGenerator(name = "drone_SEQ_GEN", sequenceName = "drone_SEQ", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "serial_number", length = 1000)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private Model model;

    @Column(name = "weight_limit", columnDefinition = "float4 default 0.0")
    @Max(value = 500, message = "The maximum value allowed is 500")
    private Float weightLimit;

    @Column(name = "battery_capacity", columnDefinition = "float4 default 0.0")
    @Max(value = 100, message = "The maximum battery capacity is 100")
    private Float batteryCapacity;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean enabled;

    @Transient
    @OneToMany(mappedBy = "drone", fetch = FetchType.LAZY)
    private List<Delivery> deliveryList;

    @Transient
    @OneToMany(mappedBy = "drone", fetch = FetchType.LAZY)
    private List<DroneBatteryHistory> droneBatteryHistoryList;


    public Drone(String serialNumber, Model model, Float weightLimit, Float batteryCapacity, State state, Boolean enabled) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.enabled = enabled;
    }
}
