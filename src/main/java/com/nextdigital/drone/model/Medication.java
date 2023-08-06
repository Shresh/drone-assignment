package com.nextdigital.drone.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "medication",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}, name = "unique_medication_name"),
        }
)
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_SEQ_GEN")
    @SequenceGenerator(name = "medication_SEQ_GEN", sequenceName = "medication_SEQ", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Only letters, numbers, hyphens, and underscores are allowed.")
    private String name;

    @Column(name = "weight", columnDefinition = "float4 default 0.0")
    private Float weight;

    @Column(name = "code")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Only uppercase letters, underscores, and numbers are allowed.")
    private String code;

    @Column(name = "image")
    @Lob
    private byte[] image;

    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean enabled;

    @Transient
    @OneToMany(mappedBy = "medication", fetch = FetchType.LAZY)
    private List<DeliveryItems> deliveryItemsList;

    public Medication(String name, Float weight, String code, byte[] image, Boolean enabled) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.enabled = enabled;
    }
}
