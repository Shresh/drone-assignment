package com.nextdigital.drone.service.impl;

import com.nextdigital.drone.api.request.DroneRequest;
import com.nextdigital.drone.api.response.DroneResponse;
import com.nextdigital.drone.config.Messages;
import com.nextdigital.drone.model.Drone;
import com.nextdigital.drone.repository.DroneRepo;
import com.nextdigital.drone.service.DroneService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepo droneRepo;
    private final Messages messages;

    @Override
    public Drone checkit(Integer id) throws NotFoundException {
        Optional<Drone> drone = droneRepo.findById(id);
        if (drone.isEmpty()) {
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
        } else {
            drone = new Drone();
            drone.setEnabled(true);
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
        return droneResponse;
    }

    @Override
    public DroneResponse getbystatus(String status) {
        return null;
    }

    @Override
    @Transactional
    public boolean changeenabled(Integer id) throws NotFoundException {
        Drone drone = checkit(id);
        drone.setEnabled(!drone.getEnabled());
        droneRepo.save(drone);
        return drone.getEnabled();
    }
}
