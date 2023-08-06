package com.nextdigital.drone.service;

import com.nextdigital.drone.api.request.DroneRequest;
import com.nextdigital.drone.api.response.DeliveryResponse;
import com.nextdigital.drone.api.response.DroneResponse;
import com.nextdigital.drone.enums.State;
import com.nextdigital.drone.model.Drone;
import javassist.NotFoundException;

import java.util.List;

public interface DroneService {
    Drone checkit(Integer id) throws NotFoundException;

    DroneResponse saveit(DroneRequest droneRequest) throws NotFoundException;

    List<DroneResponse> getall();

    DroneResponse getbyid(Integer id) throws NotFoundException;

    DroneResponse getbystatus(String status);

    void changestate(State state, Drone drone);

    boolean changeenabled(Integer id) throws NotFoundException;

    List<DroneResponse> getavailabledrones();

    List<DeliveryResponse> getdronedelivery(Integer id);

    Float getbatterybydroneid(Integer droneid) throws NotFoundException;
}
