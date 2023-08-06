package com.nextdigital.drone.repository;

import com.nextdigital.drone.model.Delivery;
import com.nextdigital.drone.pojo.DeliveryPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DeliveryRepo extends JpaRepository<Delivery, Long> {
    Delivery findByDeliveryLocation(String location);

    @Query(value = "select d.id,d.delivery_location,d.total_weight,dr.id as drone_id,dr.serial_number,di.id as delivery_item_id,di.weight,di.quantity,m.id as medication_id,m.name as medication_name\n" +
            "from delivery d inner join drone dr on dr.id = d.drone_id\n" +
            "inner join delivery_items di on di.delivery_id = d.id and d.status = true\n" +
            "inner join medication m on m.id = di.medication_id", nativeQuery = true)
    List<Map<String,Object>> getactivedelivery();

    @Query(value = "\n" +
            "select d.id,d.delivery_location,d.total_weight,dr.id as drone_id,dr.serial_number,di.id as delivery_item_id,di.weight,di.quantity,m.id as medication_id,m.name as medication_name\n" +
            "from delivery d inner join drone dr on dr.id = d.drone_id\n" +
            "inner join delivery_items di on di.delivery_id = d.id and d.status = true\n" +
            "inner join medication m on m.id = di.medication_id where d.drone_id = ?1", nativeQuery = true)
    List<Map<String,Object>> getbydroneid(Integer droneid);

    List<Delivery> getAllByStatus(Boolean status);
}
