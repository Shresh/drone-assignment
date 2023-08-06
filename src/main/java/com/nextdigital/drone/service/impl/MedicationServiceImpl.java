package com.nextdigital.drone.service.impl;

import com.nextdigital.drone.api.request.MedicationRequest;
import com.nextdigital.drone.api.response.MedicationResponse;
import com.nextdigital.drone.config.Messages;
import com.nextdigital.drone.model.Medication;
import com.nextdigital.drone.repository.MedicationRepo;
import com.nextdigital.drone.service.MedicationService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepo medicationRepo;
    private final Messages messages;

    @Override
    public Medication checkit(Integer id) throws NotFoundException {
        Optional<Medication> medication = medicationRepo.findById(id);
        if (medication.isEmpty()) {
            throw new NotFoundException(messages.get("error.id.not.found", messages.get("medication")));
        }
        return medication.get();
    }

    @Override
    @Transactional
    public MedicationResponse saveit(MedicationRequest medicationRequest) throws NotFoundException, IOException {
        Medication medication;
        if (medicationRequest.getId() != null) {
            medication = checkit(medicationRequest.getId());
        } else {
            medication = new Medication();
            medication.setEnabled(true);
        }
        medication.setName(medicationRequest.getName());
        medication.setWeight(medicationRequest.getWeight());
        medication.setCode(medicationRequest.getCode());

        if (medicationRequest.getImage() != null && !medicationRequest.getImage().isEmpty()) {
            try {
                medication.setImage(medicationRequest.getImage().getBytes());
            } catch (IOException e) {
                // Handle the exception, e.g., log it or throw a custom exception
                e.printStackTrace();
            }
        }

        Medication medication1 = medicationRepo.save(medication);

        MedicationResponse medicationResponse = new MedicationResponse();
        BeanUtils.copyProperties(medication1, medicationResponse);
        return medicationResponse;
    }

    @Override
    public List<MedicationResponse> getall() {
        List<Medication> medicationList = medicationRepo.findAll();
        return medicationList.stream()
                .map(x -> {
                    MedicationResponse medicationResponse = new MedicationResponse();
                    BeanUtils.copyProperties(x, medicationResponse);
                    return medicationResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public MedicationResponse getbyid(Integer id) throws NotFoundException {
        MedicationResponse medicationResponse = new MedicationResponse();
        BeanUtils.copyProperties(checkit(id), medicationResponse);
        return medicationResponse;
    }

    @Override
    @Transactional
    public boolean changeenabled(Integer id) throws NotFoundException {
        Medication medication = checkit(id);
        medication.setEnabled(!medication.getEnabled());
        medicationRepo.save(medication);
        return medication.getEnabled();
    }
}
