package com.nextdigital.drone.repository;

import com.nextdigital.drone.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepo extends JpaRepository<Delivery, Long> {
}
