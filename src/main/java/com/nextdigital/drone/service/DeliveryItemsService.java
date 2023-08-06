package com.nextdigital.drone.service;

import com.nextdigital.drone.api.request.DeliveryItemsRequest;
import com.nextdigital.drone.api.response.DeliveryItemsResponse;
import com.nextdigital.drone.model.Delivery;
import com.nextdigital.drone.model.DeliveryItems;
import javassist.NotFoundException;

import java.util.List;

public interface DeliveryItemsService {
    DeliveryItems checkit(Long id) throws NotFoundException;

    List<DeliveryItemsResponse> saveit(List<DeliveryItemsRequest> deliveryItemsRequestList, Delivery delivery) throws Exception;
}
