package com.nextdigital.drone.repository;

import com.nextdigital.drone.model.DeliveryItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryItemsRepo extends JpaRepository<DeliveryItems, Long> {
}
