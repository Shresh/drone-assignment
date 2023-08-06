package com.nextdigital.drone.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "delivery_items")
public class DeliveryItems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_items_SEQ_GEN")
    @SequenceGenerator(name = "delivery_items_SEQ_GEN", sequenceName = "delivery_items_SEQ", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity", columnDefinition = "int default 0")
    private Integer quantity;

    @Column(name = "weight", columnDefinition = "float4 default 0.0")
    private Float weight;

    @JoinColumn(name = "medication_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Medication medication;

    @JoinColumn(name = "delivery_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    public DeliveryItems(Integer quantity, Float weight, Medication medication, Delivery delivery) {
        this.quantity = quantity;
        this.weight = weight;
        this.medication = medication;
        this.delivery = delivery;
    }
}
