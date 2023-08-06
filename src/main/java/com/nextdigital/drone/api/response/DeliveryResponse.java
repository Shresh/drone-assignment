package com.nextdigital.drone.api.response;

import com.nextdigital.drone.model.Drone;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryResponse {
    private Long id;
    private Integer drone;
    private Boolean status;
    private String deliveryLocation;
    private float totalWeight;
    private List<DeliveryItemsResponse> deliveryItemsList;
}
