package com.company.conferenceroombooking.service.impl;

import com.company.conferenceroombooking.TestData;
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
public class MaintenanceServiceTest {

    @InjectMocks
    MaintenanceServiceImpl maintenanceService;

    @Mock
    MaintenanceTimeRepository maintenanceTimeRepository;


    @Before
    public void setUp() {

    }

    @Test
    public void testGetMaintanceTimeSlots() {
        lenient().when(maintenanceTimeRepository.findAll()).thenReturn(TestData.MAINTENANCE_TIME_LIST.get());
        List<MaintenanceTime> response = maintenanceService.getMaintenanceTimeSlots();
        assertNotNull(response);
        boolean equal = response.get(0).getStartTime().compareTo(LocalTime.of(9,0)) == 0;
        assertTrue(equal);
    }
}
