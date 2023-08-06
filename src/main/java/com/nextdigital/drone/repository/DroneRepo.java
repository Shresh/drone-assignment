package com.nextdigital.drone.repository;

import com.nextdigital.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DroneRepo extends JpaRepository<Drone, Integer> {

    @Query(value = "select d.* from drone d where d.state = 'IDLE' and d.battery_capacity >25", nativeQuery = true)
    List<Drone> getavailabledrones();

    Drone findBySerialNumber(String serialNumber);
}
