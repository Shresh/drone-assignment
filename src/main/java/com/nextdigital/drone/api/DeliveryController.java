package com.nextdigital.drone.api;

import com.nextdigital.drone.service.DeliveryService;
import com.nextdigital.drone.util.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveryservice/")
public class DeliveryController extends BaseController {
    private final DeliveryService deliveryService;
}
