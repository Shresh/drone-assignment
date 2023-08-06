package com.nextdigital.drone.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationResponse {

    private Integer id;
    private String name;
    private Float weight;
    private String code;
    private byte[] image;
}
