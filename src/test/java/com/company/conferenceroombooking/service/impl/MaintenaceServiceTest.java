package com.company.conferenceroombooking.service.impl;

import com.company.conferenceroombooking.entity.MaintenanceTime;
import com.company.conferenceroombooking.repository.MaintenanceTimeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class MaintenaceServiceTest {

    @InjectMocks
    MaintenanceServiceImpl maintenanceService;

    @Mock
    MaintenanceTimeRepository maintenanceTimeRepository;

    List<MaintenanceTime> maintenanceTimeList;
     MaintenanceTime maintenanceTime;

    @Before
    public void setUp() {
        maintenanceTimeList = new ArrayList<>();
        MaintenanceTime maintenanceTime = new MaintenanceTime();
        maintenanceTime.setEndTime(LocalTime.of(9,15));
        maintenanceTime.setStartTime(LocalTime.of(9,0));

        MaintenanceTime maintenanceTime1 = new MaintenanceTime();
        maintenanceTime1.setEndTime(LocalTime.of(15,15));
        maintenanceTime1.setStartTime(LocalTime.of(15,0));
        List<MaintenanceTime> maintenanceTimes = Arrays.asList(maintenanceTime, maintenanceTime1);
        maintenanceTimeList.addAll(maintenanceTimes);
    }

    @Test
    public void testGetMaintanceTimeSlots() {
        lenient().when(maintenanceTimeRepository.findAll()).thenReturn(maintenanceTimeList);
        List<MaintenanceTime> response = maintenanceService.getMaintenanceTimeSlots();
        assertNotNull(response);
        boolean equal = response.get(0).getStartTime().compareTo(LocalTime.of(9,0)) == 0;
        assertTrue(equal);
    }
}
