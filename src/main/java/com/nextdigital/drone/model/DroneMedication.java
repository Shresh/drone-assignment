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
@Table(name = "drone_medication")
public class DroneMedication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drone_SEQ_GEN")
    @SequenceGenerator(name = "drone_SEQ_GEN", sequenceName = "drone_SEQ", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "drone_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Drone drone;

    @JoinColumn(name = "medication_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Medication medication;

    @Column(name = "delivery_location")
    private String deliveryLocation;

    @Column(name = "total_weight")
    private float totalWeight;
}
