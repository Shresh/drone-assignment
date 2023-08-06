package com.nextdigital.drone.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryItemsRequest {
    private Long id;
    private Integer quantity;
    private Float weight;
    private Integer medication;
}
