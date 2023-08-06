package com.nextdigital.drone.api.response;

import com.nextdigital.drone.enums.Model;
import com.nextdigital.drone.enums.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneResponse {
    private Integer id;
    private String serialNumber;
    private Model model;
    private Float weightLimit;
    private float batteryCapacity;
    private State state;
    private Boolean enabled;
}
