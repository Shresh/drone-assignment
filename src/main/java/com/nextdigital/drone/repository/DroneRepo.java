package com.nextdigital.drone.repository;

import com.nextdigital.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepo extends JpaRepository<Drone, Integer> {
}
