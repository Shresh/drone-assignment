package com.nextdigital.drone.repository;

import com.nextdigital.drone.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepo extends JpaRepository<Medication, Integer> {
}
