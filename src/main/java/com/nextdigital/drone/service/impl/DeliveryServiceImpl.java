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

import java.math.BigInteger;
import java.util.*;

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
            droneService.changestate(State.LOADING, delivery.getDrone());
        } else {
            delivery = new Delivery();
            Drone drone = droneService.checkit(deliveryRequest.getDrone());
            State state = drone.getState();

            if (!state.equals(State.IDLE)) {
                log.error("Drone with id {} not available", drone.getId());
                throw new Exception(messages.get("error.not.available", messages.get("drone")));
            }
            droneService.changestate(State.LOADING, drone);


            Float battery = drone.getBatteryCapacity();
            if (battery < 25) {
                log.error("Drone with id {} battery is lower than or equal to 25%", drone.getId());
                throw new Exception(messages.get("error.battery.low", messages.get("drone")));
            }
            delivery.setDrone(drone);
            delivery.setStatus(true);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Float totalWeight = deliveryRequest.getTotalWeight();
        if (totalWeight >= delivery.getDrone().getWeightLimit()) {
            throw new Exception(messages.get("error.overweight", messages.get("delivery")));
        }
        delivery.setTotalWeight(totalWeight);
        delivery.setDeliveryLocation(deliveryRequest.getDeliveryLocation());

        Delivery delivery1 = deliveryRepo.save(delivery);
        List<DeliveryItemsResponse> deliveryItemsResponse = deliveryItemsService.saveit(deliveryRequest.getDeliveryItems(), delivery1);

        droneService.changestate(State.LOADED, delivery1.getDrone());

        DeliveryResponse deliveryResponse = new DeliveryResponse();
        BeanUtils.copyProperties(deliveryRequest, deliveryResponse);
        deliveryResponse.setId(delivery1.getId());
        deliveryResponse.setStatus(delivery1.getStatus());
        return deliveryResponse;
    }

    /*@Override
    public List<DeliveryResponse> getactivedelivery() {
        List<Delivery> deliveryList = deliveryRepo.getAllByStatus(true);
        List<DeliveryResponse> deliveryResponses = deliveryList.stream().map(x -> {
            DeliveryResponse deliveryResponse = new DeliveryResponse();
            BeanUtils.copyProperties(x, deliveryResponse);
            deliveryResponse.setDrone(x.getDrone().getId());

            List<DeliveryItemsResponse> deliveryItemsResponseList = new ArrayList<>();
            for (DeliveryItems d : x.getDeliveryItemsList()) {
                DeliveryItemsResponse deliveryItemsResponse = new DeliveryItemsResponse();
                BeanUtils.copyProperties(d, deliveryItemsResponse);
                deliveryItemsResponse.setMedication(d.getMedication().getId());
                deliveryItemsResponseList.add(deliveryItemsResponse);
            }
            deliveryResponse.setDeliveryItemsList(deliveryItemsResponseList);

            return deliveryResponse;
        }).collect(Collectors.toList());
        return deliveryResponses;
    }*/

    private List<Map<String, Object>> formatDelivery(List<Map<String, Object>> deliveryList) {

        Set<Map<String, Object>> deliveryResponsesSet = new HashSet<>();
        for (Map<String, Object> x : deliveryList) {
            Map<String, Object> deliveryResponse = new HashMap<>();
            deliveryResponse.put("id", x.get("id"));
            deliveryResponse.put("delivery_location", x.get("delivery_location"));
            deliveryResponse.put("total_weight", x.get("total_weight"));
            deliveryResponse.put("drone_id", x.get("drone_id"));
            deliveryResponse.put("serial_number", x.get("serial_number"));
            deliveryResponsesSet.add(deliveryResponse);
        }
        List<Map<String, Object>> deliveryResponses = new ArrayList<>(deliveryResponsesSet);

        for (Map<String, Object> deliveryResponse : deliveryResponses) {
            Long deliveryId = ((BigInteger) deliveryResponse.get("id")).longValue();
            List<DeliveryItemsResponse> deliveryItemsResponseList = new ArrayList<>();
            for (Map<String, Object> x : deliveryList) {
                Long id = ((BigInteger) x.get("id")).longValue();
                if (deliveryId.equals(id)) {
                    DeliveryItemsResponse deliveryItemsResponse = new DeliveryItemsResponse();
                    Long delivery_item_id = ((BigInteger) x.get("delivery_item_id")).longValue();
                    deliveryItemsResponse.setId(delivery_item_id);
                    deliveryItemsResponse.setWeight((Float) x.get("weight"));
                    deliveryItemsResponse.setQuantity((Integer) x.get("quantity"));
                    Integer medicationId = (Integer) x.get("medication_id");
                    deliveryItemsResponse.setMedication(medicationId);
                    deliveryItemsResponse.setName(x.get("medication_name").toString());
                    deliveryItemsResponseList.add(deliveryItemsResponse);
                }
            }
            deliveryResponse.put("deliveryItem", deliveryItemsResponseList);
        }
        return deliveryResponses;
    }

    @Override
    public List<Map<String, Object>> getactivedelivery() {
        List<Map<String, Object>> deliveryList = deliveryRepo.getactivedelivery();
        return formatDelivery(deliveryList);
    }

    @Override
    public List<Map<String, Object>> getbydroneid(Integer droneid) throws NotFoundException {
        Drone drone = droneService.checkit(droneid);
        List<Map<String, Object>> deliveryList = deliveryRepo.getbydroneid(drone.getId());
        return formatDelivery(deliveryList);
    }
}
