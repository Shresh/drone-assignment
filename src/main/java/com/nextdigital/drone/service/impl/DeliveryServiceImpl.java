package com.nextdigital.drone.service.impl;

import com.nextdigital.drone.config.Messages;
import com.nextdigital.drone.repository.DeliveryRepo;
import com.nextdigital.drone.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepo deliveryRepo;
    private final Messages messages;
}
