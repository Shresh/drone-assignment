package com.nextdigital.drone.api.request;

import com.nextdigital.drone.model.Drone;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryRequest {
    private Long id;
    private Integer drone;
    private Boolean status;
    private String deliveryLocation;
    private float totalWeight;
    private List<DeliveryItemsRequest> deliveryItems;
}
