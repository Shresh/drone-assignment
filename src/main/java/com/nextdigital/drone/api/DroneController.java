package com.nextdigital.drone.api;

import com.nextdigital.drone.api.request.DroneRequest;
import com.nextdigital.drone.api.response.DroneResponse;
import com.nextdigital.drone.service.DroneService;
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
@RequestMapping("/api/drone/")
public class DroneController extends BaseController {
    private final DroneService droneService;

    @PostMapping(value = "save")
    public ResponseEntity<?> save(@Valid @RequestBody DroneRequest droneRequest, BindingResult bindingResult) throws Exception {
        validateRequestBody(bindingResult);
        String msg;
        try {
            msg = droneRequest.getId() == null ? "success.save" : "success.update";
            DroneResponse droneResponse = droneService.saveit(droneRequest);
            return ResponseEntity.ok(successResponse(customMessageSource.get(msg, customMessageSource.get("drone")), droneResponse));
        } catch (Exception e) {
            e.printStackTrace();
            msg = droneRequest.getId() == null ? "error.save" : "error.update";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get(msg, customMessageSource.get("drone")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) throws NotFoundException {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched", customMessageSource.get("drone")), droneService.getbyid(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched", customMessageSource.get("drone")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getdronedelivery/{id}")
    public ResponseEntity<?> getDroneDelivery(@PathVariable("id") Integer id) throws NotFoundException {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched", customMessageSource.get("drone")), droneService.getdronedelivery(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched", customMessageSource.get("drone")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getbatterybydroneid/{id}")
    public ResponseEntity<?> getBatteryByDroneid(@PathVariable("id") Integer id) throws NotFoundException {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched", customMessageSource.get("drone")), droneService.getbatterybydroneid(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched", customMessageSource.get("drone")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getall")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched.list", customMessageSource.get("drone")), droneService.getall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched.list", customMessageSource.get("drone")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getavailabledrones")
    public ResponseEntity<?> getAvailableDrones() {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched.list", customMessageSource.get("drone")), droneService.getavailabledrones()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched.list", customMessageSource.get("drone")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "changeenabled/{id}")
    public ResponseEntity<?> changeEnabled(@PathVariable("id") Integer id) throws NotFoundException {
        String msg;
        try {
            boolean statusValue = droneService.changeenabled(id);
            msg = statusValue ? "success.activation" : "success.deactivation";
            return ResponseEntity.ok(successResponse(customMessageSource.get(msg, customMessageSource.get("drone")), statusValue));
        } catch (Exception e) {
            msg = "error.activation";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get(msg, customMessageSource.get("drone")), e.getLocalizedMessage()));
        }
    }
}
