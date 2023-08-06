package com.nextdigital.drone.util;

import com.nextdigital.drone.enums.Model;
import com.nextdigital.drone.enums.State;
import com.nextdigital.drone.model.Delivery;
import com.nextdigital.drone.model.DeliveryItems;
import com.nextdigital.drone.model.Drone;
import com.nextdigital.drone.model.Medication;
import com.nextdigital.drone.repository.DeliveryItemsRepo;
import com.nextdigital.drone.repository.DeliveryRepo;
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
    private final DeliveryRepo deliveryRepo;
    private final DeliveryItemsRepo deliveryItemsRepo;

    public void initialSetup() {
        addDrones();
        addMedication();
        addDelivery();
        addDeliveryItem();
    }

    private void addDeliveryItem() {
        DeliveryItems deliveryItems1 = setDeliveryItems(1, 22.0f, "Medicine_A", "Kathmandu");
        DeliveryItems deliveryItems2 = setDeliveryItems(2, 30.0f, "Medicine_B", "Kathmandu");
        DeliveryItems deliveryItems3 = setDeliveryItems(1, 40.0f, "Medicine_C", "Bhaktapur");
        DeliveryItems deliveryItems4 = setDeliveryItems(2, 30.0f, "Medicine_B", "Bhaktapur");
        List<DeliveryItems> deliveryItemsList = Arrays.asList(deliveryItems1, deliveryItems2, deliveryItems3, deliveryItems4);
        deliveryItemsRepo.saveAll(deliveryItemsList);
    }

    private void addDelivery() {
        Delivery delivery1 = setDelivery("MNO345", "Kathmandu", 82.0f);
        Delivery delivery2 = setDelivery("BCD890", "Bhaktapur", 100f);
        List<Delivery> deliveryList = Arrays.asList(delivery1, delivery2);
        deliveryRepo.saveAll(deliveryList);
    }


    private void addMedication() {
        Medication medication1 = setMedication("Medicine_A", 22.0f, "ABC123", null, true);

        Medication medication2 = setMedication("Medicine_B", 30.0f, "DEF456", null, true);


        Medication medication3 = setMedication("Medicine_C", 40.0f, "GHI789", null, true);

        List<Medication> medicationList = Arrays.asList(medication1, medication2, medication3);
        medicationRepo.saveAll(medicationList);
    }

    private void addDrones() {
        Drone drone1 = setDrone("ABC123", Model.Lightweight, 100.0f, 100.f, State.IDLE, true);
        Drone drone2 = setDrone("DEF456", Model.Middleweight, 150.0f, 90.0f, State.IDLE, true);
        Drone drone3 = setDrone("GHI789", Model.Cruiserweight, 200.0f, 80.0f, State.LOADED, true);
        Drone drone4 = setDrone("JKL012", Model.Heavyweight, 250.0f, 90.0f, State.DELIVERING, true);
        Drone drone5 = setDrone("MNO345", Model.Lightweight, 120.0f, 60.0f, State.DELIVERED, true);
        Drone drone6 = setDrone("PQR678", Model.Middleweight, 150.0f, 30.0f, State.RETURNING, true);
        Drone drone7 = setDrone("STU901", Model.Cruiserweight, 210.0f, 25.0f, State.IDLE, true);
        Drone drone8 = setDrone("VWX234", Model.Heavyweight, 280.0f, 100.0f, State.LOADING, true);
        Drone drone9 = setDrone("YZA567", Model.Lightweight, 130.0f, 70.0f, State.LOADED, true);
        Drone drone10 = setDrone("BCD890", Model.Middleweight, 160.0f, 80.0f, State.DELIVERING, true);

        List<Drone> droneList = Arrays.asList(drone1, drone2, drone3, drone4, drone5, drone6, drone7, drone8, drone9, drone10);

        droneRepo.saveAll(droneList);

    }

    private Drone setDrone(String serialNumber, Model model, Float weightLimit, Float batteryCapacity, State state, Boolean enable) {
        return new Drone(serialNumber, model, weightLimit, batteryCapacity, state, enable);
    }

    private Medication setMedication(String name, Float weight, String code, byte[] image, Boolean enable) {
        return new Medication(name, weight, code, image, enable);
    }

    private Delivery setDelivery(String serialNumber, String deliveryLocation, Float totalWeight) {
        Drone drone = droneRepo.findBySerialNumber(serialNumber);
        return new Delivery(drone, true, deliveryLocation, totalWeight);
    }

    private DeliveryItems setDeliveryItems(Integer quantity, Float weight, String medicationName, String deliveryLocation) {
        Medication medication = medicationRepo.findByName(medicationName);
        Delivery delivery = deliveryRepo.findByDeliveryLocation(deliveryLocation);
        return new DeliveryItems(quantity, weight, medication, delivery);
    }
}
