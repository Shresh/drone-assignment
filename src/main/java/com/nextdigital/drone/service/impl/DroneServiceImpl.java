package com.nextdigital.drone.service.impl;

import com.nextdigital.drone.api.request.DroneRequest;
import com.nextdigital.drone.api.response.DeliveryResponse;
import com.nextdigital.drone.api.response.DroneResponse;
import com.nextdigital.drone.config.Messages;
import com.nextdigital.drone.enums.State;
import com.nextdigital.drone.model.Drone;
import com.nextdigital.drone.repository.DroneRepo;
import com.nextdigital.drone.service.DroneService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DroneServiceImpl implements DroneService {

    private final DroneRepo droneRepo;
    private final Messages messages;

    @Override
    public Drone checkit(Integer id) throws NotFoundException {
        Optional<Drone> drone = droneRepo.findById(id);
        if (drone.isEmpty()) {
            log.error("Drone data with id {} not found", id);
            throw new NotFoundException(messages.get("error.id.not.found", messages.get("drone")));
        }
        return drone.get();
    }

    @Override
    @Transactional
    public DroneResponse saveit(DroneRequest droneRequest) throws NotFoundException {
        Drone drone;
        if (droneRequest.getId() != null) {
            drone = checkit(droneRequest.getId());
            log.info("Drone with id {} is being updated.", droneRequest.getId());
        } else {
            drone = new Drone();
            drone.setEnabled(true);
            log.info("New drone is being added.");
        }
        drone.setSerialNumber(droneRequest.getSerialNumber());
        drone.setModel(droneRequest.getModel());
        drone.setWeightLimit(droneRequest.getWeightLimit());
        drone.setBatteryCapacity(droneRequest.getBatteryCapacity());
        drone.setState(droneRequest.getState());

        Drone drone1 = droneRepo.save(drone);
        DroneResponse droneResponse = new DroneResponse();
        BeanUtils.copyProperties(drone1, droneResponse);
        return droneResponse;
    }

    @Override
    public List<DroneResponse> getall() {
        List<Drone> droneList = droneRepo.findAll();
        log.info("Fetching list of all the drones");
        return droneList.stream().map(x -> {
            DroneResponse droneResponse = new DroneResponse();
            BeanUtils.copyProperties(x, droneResponse);
            return droneResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public DroneResponse getbyid(Integer id) throws NotFoundException {
        DroneResponse droneResponse = new DroneResponse();
        BeanUtils.copyProperties(checkit(id), droneResponse);
        log.info("Fetching drone detail with id {}", droneResponse.getId());
        return droneResponse;
    }

    @Override
    public DroneResponse getbystatus(String status) {
        return null;
    }

    @Override
    @Transactional
    public void changestate(State state, Drone drone) {
        drone.setState(state);
        droneRepo.save(drone);
    }

    @Override
    @Transactional
    public boolean changeenabled(Integer id) throws NotFoundException {
        Drone drone = checkit(id);
        drone.setEnabled(!drone.getEnabled());
        droneRepo.save(drone);
        log.info("Activating/Deactivating drone with id {}", drone.getId());
        return drone.getEnabled();
    }

    @Override
    public List<DroneResponse> getavailabledrones() {
        List<Drone> droneList = droneRepo.getavailabledrones();

        return droneList.stream().map(x -> {
            DroneResponse droneResponse = new DroneResponse();
            BeanUtils.copyProperties(x, droneResponse);
            return droneResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DeliveryResponse> getdronedelivery(Integer id) {
        return null;
    }
}
