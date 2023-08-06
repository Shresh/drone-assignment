package com.nextdigital.drone.service;

import com.nextdigital.drone.api.request.DeliveryRequest;
import com.nextdigital.drone.api.response.DeliveryResponse;
import com.nextdigital.drone.model.Delivery;
import javassist.NotFoundException;

public interface DeliveryService {
    Delivery checkit(Long id) throws NotFoundException;

    DeliveryResponse saveit(DeliveryRequest deliveryRequest) throws Exception;
}
