package com.nextdigital.drone.repository;

import com.nextdigital.drone.model.DroneBatteryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneBatteryHistoryRepo extends JpaRepository<DroneBatteryHistory, Long> {
}
