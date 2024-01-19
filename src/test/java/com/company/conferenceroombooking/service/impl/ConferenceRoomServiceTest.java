package com.company.conferenceroombooking.service.impl;

import com.company.conferenceroombooking.TestData;
import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import com.company.conferenceroombooking.mapper.ConferenceModelEntityMapper;
import com.company.conferenceroombooking.repository.ConferenceRoomRepository;
import com.company.conferenceroombooking.service.helper.ConferenceRoomServiceHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomServiceTest {

    @InjectMocks
    ConferenceRoomServiceImpl roomService;

    @Mock
    ConferenceRoomServiceHelper roomServiceHelper;
    @Mock
    ConferenceRoomRepository roomRepository;
    @Mock
    ConferenceModelEntityMapper mapper;

    @Before
    public void setUp() {

    }

    @Test
    public void testBookAConferenceRoom() {
        lenient().when(roomRepository.findAll()).thenReturn(TestData.CONFERENCE_ROOM_DTO_LIST.get());
        lenient().when(roomServiceHelper.getAllAvailableConferenceRooms(any(), anyString(), anyString())).thenReturn(TestData.CONFERENCE_ROOM_DTO_LIST.get());
        lenient().when(mapper.maptoConferenceRoomResponse(any())).thenReturn(TestData.CONFERENCE_ROOM_RESPONSE_DTO_LIST.get());

        List<ConferenceRoomResponseDto> response = roomService.getAvailableConferenceRooms(LocalTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER)),LocalTime.now().plusHours(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER)));
        assertNotNull(response);
        assertTrue(response.get(0).getName().equalsIgnoreCase("Amaze"));
    }
}
