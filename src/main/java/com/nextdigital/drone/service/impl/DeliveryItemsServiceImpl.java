package com.nextdigital.drone.service.impl;

import com.nextdigital.drone.api.request.DeliveryItemsRequest;
import com.nextdigital.drone.api.response.DeliveryItemsResponse;
import com.nextdigital.drone.config.Messages;
import com.nextdigital.drone.model.Delivery;
import com.nextdigital.drone.model.DeliveryItems;
import com.nextdigital.drone.model.Medication;
import com.nextdigital.drone.repository.DeliveryItemsRepo;
import com.nextdigital.drone.service.DeliveryItemsService;
import com.nextdigital.drone.service.MedicationService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryItemsServiceImpl implements DeliveryItemsService {
    private final DeliveryItemsRepo deliveryItemsRepo;
    private final Messages messages;
    private final MedicationService medicationService;

    @Override
    public DeliveryItems checkit(Long id) throws NotFoundException {
        Optional<DeliveryItems> deliveryItems = deliveryItemsRepo.findById(id);
        if (deliveryItems.isEmpty()) {
            log.error("Delivery Items data with id {} not found", id);
            throw new NotFoundException(messages.get("error.id.not.found", messages.get("delivery.items")));
        }
        return deliveryItems.get();
    }

    @Override
    @Transactional
    public List<DeliveryItemsResponse> saveit(List<DeliveryItemsRequest> deliveryItemsRequestList, Delivery delivery) throws Exception {

        List<DeliveryItems> deliveryItemsArrayList = new ArrayList<>();
        for (DeliveryItemsRequest deliveryItemsRequest : deliveryItemsRequestList) {
            DeliveryItems deliveryItems;
            if (deliveryItemsRequest.getId() != null) {
                deliveryItems = checkit(deliveryItemsRequest.getId());
            } else {
                deliveryItems = new DeliveryItems();
                deliveryItems.setDelivery(delivery);
            }
            Medication medication = medicationService.checkit(deliveryItemsRequest.getMedication());
            deliveryItems.setMedication(medication);

            Integer quantity = deliveryItemsRequest.getQuantity();
            deliveryItems.setQuantity(quantity);
            deliveryItems.setWeight(deliveryItemsRequest.getWeight());

            deliveryItemsArrayList.add(deliveryItems);

        }

        deliveryItemsRepo.saveAll(deliveryItemsArrayList);
        List<DeliveryItemsResponse> deliveryItemsResponseList = new ArrayList<>();
        BeanUtils.copyProperties(deliveryItemsRequestList, deliveryItemsResponseList);

        return deliveryItemsResponseList;
    }
}
