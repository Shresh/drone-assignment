package com.nextdigital.drone.api.response;

import com.nextdigital.drone.model.Medication;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryItemsResponse {
    private Long id;
    private Integer quantity;
    private float weight;
    private MedicationResponse medication;
}
