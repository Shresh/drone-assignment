package com.nextdigital.drone.util;

import com.nextdigital.drone.enums.Model;
import com.nextdigital.drone.enums.State;
import com.nextdigital.drone.model.Drone;
import com.nextdigital.drone.model.Medication;
import com.nextdigital.drone.repository.DroneRepo;
import com.nextdigital.drone.repository.MedicationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class InitialCreator {
    private final DroneRepo droneRepo;
    private final MedicationRepo medicationRepo;

    public void initialSetup() {
        addDrones();
        addMedication();
    }

    private void addMedication() {
        Medication medication1 = setMedication("Medicine_A", 22.0f, "ABC123", null, true);

        Medication medication2 = setMedication("Medicine_B", 200.0f, "DEF456", null, true);


        Medication medication3 = setMedication("Medicine_C", 100.0f, "GHI789", null, true);

        List<Medication> medicationList = Arrays.asList(medication1, medication2, medication3);
        medicationRepo.saveAll(medicationList);
    }

    public void addDrones() {
        Drone drone1 = setDrone("ABC123", Model.Lightweight, 100.0f, 200.0f, State.IDLE, true);
        Drone drone2 = setDrone("DEF456", Model.Middleweight, 150.0f, 300.0f, State.LOADING, true);
        Drone drone3 = setDrone("GHI789", Model.Cruiserweight, 200.0f, 400.0f, State.LOADED, true);
        Drone drone4 = setDrone("JKL012", Model.Heavyweight, 250.0f, 500.0f, State.DELIVERING, true);
        Drone drone5 = setDrone("MNO345", Model.Lightweight, 120.0f, 220.0f, State.DELIVERED, true);
        Drone drone6 = setDrone("PQR678", Model.Middleweight, 180.0f, 320.0f, State.RETURNING, true);
        Drone drone7 = setDrone("STU901", Model.Cruiserweight, 210.0f, 420.0f, State.IDLE, true);
        Drone drone8 = setDrone("VWX234", Model.Heavyweight, 280.0f, 480.0f, State.LOADING, true);
        Drone drone9 = setDrone("YZA567", Model.Lightweight, 130.0f, 240.0f, State.LOADED, true);
        Drone drone10 = setDrone("BCD890", Model.Middleweight, 160.0f, 340.0f, State.DELIVERING, true);

        List<Drone> droneList = Arrays.asList(drone1, drone2, drone3, drone4, drone5, drone6, drone7, drone8, drone9, drone10);

        droneRepo.saveAll(droneList);

    }

    private Drone setDrone(String serialNumber, Model model, Float weightLimit, Float batteryCapacity, State state, Boolean enable) {
        return new Drone(serialNumber, model, weightLimit, batteryCapacity, state, enable);
    }

    private Medication setMedication(String name, Float weight, String code, byte[] image, Boolean enable) {
        return new Medication(name, weight, code, image, enable);
    }
}
