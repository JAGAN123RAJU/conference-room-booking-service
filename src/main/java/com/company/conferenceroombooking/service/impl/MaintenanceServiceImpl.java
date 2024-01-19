package com.company.conferenceroombooking.service.impl;

import com.company.conferenceroombooking.entity.MaintenanceTime;
import com.company.conferenceroombooking.repository.MaintenanceTimeRepository;
import com.company.conferenceroombooking.service.MaintenanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceTimeRepository maintenanceTimeRepository;

    @Override
    public List<MaintenanceTime> getMaintenanceTimeSlots() {
        return maintenanceTimeRepository.findAll();
    }
}
