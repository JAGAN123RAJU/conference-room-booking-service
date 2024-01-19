package com.company.conferenceroombooking.web;

import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.domain.response.ResponseDto;
import com.company.conferenceroombooking.service.ConferenceRoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRoomControllerTest {

    @InjectMocks
    ConferenceRoomController conferenceRoomController;
    @Mock
    ConferenceRoomService conferenceRoomService;
    List<ConferenceRoomResponseDto> conferenceRoomResponseDtos;

    @Before
    public void setUp() {
        conferenceRoomResponseDtos = new ArrayList<>();
        ConferenceRoomResponseDto responseDto1 = ConferenceRoomResponseDto.builder().id(1).capacity(3).name("Amaze").build();
        ConferenceRoomResponseDto responseDto2 = ConferenceRoomResponseDto.builder().id(2).capacity(7).name("Beauty").build();
        ConferenceRoomResponseDto responseDto3 = ConferenceRoomResponseDto.builder().id(3).capacity(12).name("Inspire").build();
        ConferenceRoomResponseDto responseDto4 = ConferenceRoomResponseDto.builder().id(4).capacity(20).name("Strive").build();
        conferenceRoomResponseDtos.addAll(Arrays.asList(responseDto1, responseDto2, responseDto3, responseDto4));
    }

    @Test
    public void testGetAvailableConferenceRooms() {
        lenient().when(conferenceRoomService.getAvailableConferenceRooms("01:00", "02:00")).thenReturn(conferenceRoomResponseDtos);
        ResponseEntity<ResponseDto> response = conferenceRoomController.getAvailableConferenceRooms("01:00", "02:00");
        assertNotNull(response);
        assertNotNull(response.getBody());
    }
}
