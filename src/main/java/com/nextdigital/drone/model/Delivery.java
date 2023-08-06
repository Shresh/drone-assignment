package com.nextdigital.drone.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "delivery")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_SEQ_GEN")
    @SequenceGenerator(name = "delivery_SEQ_GEN", sequenceName = "delivery_SEQ", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "drone_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Drone drone;

    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;

    /**
     * later can add location's lat long and check whether the drone is in location or not
     * distance
     * time assumptions
     * and other parameters
     */
    @Column(name = "delivery_location")
    private String deliveryLocation;

    @Column(name = "total_weight", columnDefinition = "float4 default 0.0")
    private Float totalWeight;

    @Transient
    @OneToMany(mappedBy = "delivery", fetch = FetchType.EAGER)
    private List<DeliveryItems> deliveryItemsList;

    public Delivery(Drone drone, Boolean status, String deliveryLocation, Float totalWeight) {
        this.drone = drone;
        this.status = status;
        this.deliveryLocation = deliveryLocation;
        this.totalWeight = totalWeight;
    }

    public Delivery(Drone drone, Boolean status, String deliveryLocation, Float totalWeight, List<DeliveryItems> deliveryItemsList) {
        this.drone = drone;
        this.status = status;
        this.deliveryLocation = deliveryLocation;
        this.totalWeight = totalWeight;
        this.deliveryItemsList = deliveryItemsList;
    }
}
