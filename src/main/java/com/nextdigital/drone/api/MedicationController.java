package com.nextdigital.drone.api;

import com.nextdigital.drone.api.request.MedicationRequest;
import com.nextdigital.drone.api.response.MedicationResponse;
import com.nextdigital.drone.service.MedicationService;
import com.nextdigital.drone.util.BaseController;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medication/")
public class MedicationController extends BaseController {
    private final MedicationService medicationService;

    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@Valid @ModelAttribute MedicationRequest medicationRequest, BindingResult bindingResult) throws Exception {
        validateRequestBody(bindingResult);
        String msg;
        try {
            msg = medicationRequest.getId() == null ? "success.save" : "success.update";
            MedicationResponse medicationResponse = medicationService.saveit(medicationRequest);
            return ResponseEntity.ok(successResponse(customMessageSource.get(msg, customMessageSource.get("medication")), medicationResponse));
        } catch (Exception e) {
            e.printStackTrace();
            msg = medicationRequest.getId() == null ? "error.save" : "error.update";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get(msg, customMessageSource.get("medication")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getbyid/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) throws NotFoundException {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched", customMessageSource.get("medication")), medicationService.getbyid(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched", customMessageSource.get("medication")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "getall")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(successResponse(customMessageSource.get("fetched.list", customMessageSource.get("medication")), medicationService.getall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get("error.fetched.list", customMessageSource.get("medication")), e.getLocalizedMessage()));
        }
    }

    @GetMapping(value = "changeenabled/{id}")
    public ResponseEntity<?> changeEnabled(@PathVariable("id") Integer id) throws NotFoundException {
        String msg;
        try {
            boolean statusValue = medicationService.changeenabled(id);
            msg = statusValue ? "success.activation" : "success.deactivation";
            return ResponseEntity.ok(successResponse(customMessageSource.get(msg, customMessageSource.get("medication")), statusValue));
        } catch (Exception e) {
            msg = "error.activation";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse(customMessageSource.get(msg, customMessageSource.get("medication")), e.getLocalizedMessage()));
        }
    }
}
