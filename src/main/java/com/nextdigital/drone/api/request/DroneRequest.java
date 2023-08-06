package com.nextdigital.drone.api.request;

import com.nextdigital.drone.enums.Model;
import com.nextdigital.drone.enums.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneRequest {
    private Integer id;
    private String serialNumber;
    private Model model;
    private Float weightLimit;
    private float batteryCapacity;
    private State state;
    private Boolean enabled = true;
}
