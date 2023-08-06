package com.nextdigital.drone.service;

import com.nextdigital.drone.model.DroneBatteryHistory;

import java.util.*;

public interface DroneBatteryHistoryService {
    void saveall(List<DroneBatteryHistory> droneBatteryHistoryList);
}
