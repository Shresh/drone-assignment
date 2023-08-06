package com.nextdigital.drone.batch;

import com.nextdigital.drone.model.Drone;
import com.nextdigital.drone.model.DroneBatteryHistory;
import com.nextdigital.drone.repository.DroneRepo;
import com.nextdigital.drone.service.DroneBatteryHistoryService;
import com.nextdigital.drone.service.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class DroneBatteryCheck {

    @Autowired
    private DroneBatteryHistoryService droneBatteryHistoryService;

    @Autowired
    private DroneRepo droneRepo;

    @Scheduled(cron = "0 0/5 * * * *")
    public void syncDroneBattery() {
        log.info("started schedular");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        List<Drone> drones = droneRepo.findAll();
        if (drones.size() > 0) {
            executor.execute(() -> {
                while (true) {
                    List<DroneBatteryHistory> droneBatteryHistoryList = new ArrayList<>();
                    for (Drone drone : drones) {
                        log.info("Drone with serial number {} has battery level {}", drone.getSerialNumber(), drone.getBatteryCapacity());
                        DroneBatteryHistory droneBatteryHistory = new DroneBatteryHistory(drone, drone.getBatteryCapacity());
                        droneBatteryHistoryList.add(droneBatteryHistory);
                    }
                    droneBatteryHistoryService.saveall(droneBatteryHistoryList);
                }
            });
        }
    }
}
