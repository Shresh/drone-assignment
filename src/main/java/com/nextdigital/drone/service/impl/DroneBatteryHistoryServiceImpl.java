package com.nextdigital.drone.service.impl;

import com.nextdigital.drone.config.Messages;
import com.nextdigital.drone.model.DroneBatteryHistory;
import com.nextdigital.drone.repository.DroneBatteryHistoryRepo;
import com.nextdigital.drone.service.DroneBatteryHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DroneBatteryHistoryServiceImpl implements DroneBatteryHistoryService {
    private final DroneBatteryHistoryRepo droneBatteryHistoryRepo;
    private final Messages messages;

    @Override
    public void saveall(List<DroneBatteryHistory> droneBatteryHistoryList) {
        droneBatteryHistoryRepo.saveAll(droneBatteryHistoryList);
    }
}
