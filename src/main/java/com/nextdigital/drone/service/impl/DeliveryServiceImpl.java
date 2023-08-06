package com.nextdigital.drone.service.impl;

import com.nextdigital.drone.api.request.DeliveryRequest;
import com.nextdigital.drone.api.response.DeliveryItemsResponse;
import com.nextdigital.drone.api.response.DeliveryResponse;
import com.nextdigital.drone.config.Messages;
import com.nextdigital.drone.enums.State;
import com.nextdigital.drone.model.Delivery;
import com.nextdigital.drone.model.Drone;
import com.nextdigital.drone.repository.DeliveryRepo;
import com.nextdigital.drone.service.DeliveryItemsService;
import com.nextdigital.drone.service.DeliveryService;
import com.nextdigital.drone.service.DroneService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepo deliveryRepo;
    private final Messages messages;
    private final DroneService droneService;
    private final DeliveryItemsService deliveryItemsService;

    @Override
    public Delivery checkit(Long id) throws NotFoundException {
        Optional<Delivery> delivery = deliveryRepo.findById(id);
        if (delivery.isEmpty()) {
            log.error("Delivery data with id {} not found", id);
            throw new NotFoundException(messages.get("error.id.not.found", messages.get("delivery")));
        }
        return delivery.get();
    }

    @Override
    @Transactional
    public DeliveryResponse saveit(DeliveryRequest deliveryRequest) throws Exception {
        Delivery delivery;
        if (deliveryRequest.getId() != null) {
            delivery = checkit(deliveryRequest.getId());
        } else {
            delivery = new Delivery();
            Drone drone = droneService.checkit(deliveryRequest.getDrone());
            State state = drone.getState();

            if (!state.equals(State.IDLE)) {
                throw new Exception(messages.get("error.not.available", messages.get("drone")));
            }

            Float battery = drone.getBatteryCapacity();
            if (battery < 25) {
                throw new Exception(messages.get("error.battery.low", messages.get("drone")));
            }
            delivery.setDrone(drone);
            delivery.setStatus(true);
        }
        Float totalWeight = deliveryRequest.getTotalWeight();
        if (totalWeight >= delivery.getDrone().getWeightLimit()) {
            throw new Exception(messages.get("error.overweight", messages.get("delivery")));
        }
        delivery.setTotalWeight(totalWeight);
        delivery.setDeliveryLocation(deliveryRequest.getDeliveryLocation());

        Delivery delivery1 = deliveryRepo.save(delivery);
        List<DeliveryItemsResponse> deliveryItemsResponse = deliveryItemsService.saveit(deliveryRequest.getDeliveryItems(), delivery1);

        DeliveryResponse deliveryResponse = new DeliveryResponse();
        BeanUtils.copyProperties(deliveryRequest, deliveryResponse);

        return deliveryResponse;
    }
}
