package com.nextdigital.drone.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryPojo {
    private Long id;
    private String deliveryLocation;
    private Float totalWeight;
    private Integer droneId;
    private Long deliveryItemId;
    private Float weight;
    private Integer quantity;
    private Integer medicationId;
}
