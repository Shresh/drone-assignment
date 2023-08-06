package com.nextdigital.drone.api;

import com.nextdigital.drone.api.request.DeliveryRequest;
import com.nextdigital.drone.api.response.DeliveryResponse;
import com.nextdigital.drone.service.DeliveryService;
import com.nextdigital.drone.util.BaseController;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveryservice/")
public class DeliveryController extends BaseController {
    private final DeliveryService deliveryService;

    @PostMapping(value = "save")
    public ResponseEntity<?> save(@Valid @RequestBody DeliveryRequest deliveryRequest, BindingResult bindingResult) throws Exception {
        validateRequestBody(bindingResult);
        String msg;
        try {
            msg = deliveryRequest.getId() == null ? "success.save" : "success.update";
            DeliveryResponse deliveryResponse = deliveryService.saveit(deliveryRequest);
            return ResponseEntity.ok(successResponse(customMessageSource.get(msg, customMessageSource.get("delivery")), deliveryResponse));
        } catch (Exception e) {
            e.printStackTrace();
            msg = deliveryRequest.getId() == null ? "error.save" : "error.update";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get(msg, customMessageSource.get("delivery")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getactivedelivery")
    public ResponseEntity<?> getDroneDelivery()  {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched", customMessageSource.get("delivery")), deliveryService.getactivedelivery()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched", customMessageSource.get("delivery")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getbydroneid/{droneid}")
    public ResponseEntity<?> getbydroneid(@PathVariable(name = "droneid") Integer droneid)  {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched", customMessageSource.get("delivery")), deliveryService.getbydroneid(droneid)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched", customMessageSource.get("delivery")), e.getLocalizedMessage()));
        }
    }
}
