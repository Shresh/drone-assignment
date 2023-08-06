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

    /**
     * later can add location's lat long and check whether the drone is in location or not
     * distance
     * time assumptions
     * and other parameters
     */
    @Column(name = "delivery_location")
    private String deliveryLocation;

    @Column(name = "total_weight", columnDefinition = "float4 default 0.0")
    private float totalWeight;

    @Transient
    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY)
    private List<DeliveryItems> deliveryItemsList;
}
