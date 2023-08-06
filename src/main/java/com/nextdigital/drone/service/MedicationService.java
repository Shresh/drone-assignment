package com.nextdigital.drone.service;

import com.nextdigital.drone.api.request.MedicationRequest;
import com.nextdigital.drone.api.response.MedicationResponse;
import com.nextdigital.drone.model.Medication;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface MedicationService {
    Medication checkit(Integer id) throws NotFoundException;

    MedicationResponse saveit(MedicationRequest medicationRequest) throws NotFoundException, IOException;

    List<MedicationResponse> getall();

    MedicationResponse getbyid(Integer id) throws NotFoundException;

    boolean changeenabled(Integer id) throws NotFoundException;
}
