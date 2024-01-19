package com.company.conferenceroombooking.service;

import com.company.conferenceroombooking.entity.MaintenanceTime;

import java.util.List;

public interface MaintenanceService {

    List<MaintenanceTime> getMaintenanceTimeSlots();

}
