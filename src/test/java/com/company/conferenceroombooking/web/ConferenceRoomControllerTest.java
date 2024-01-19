package com.company.conferenceroombooking.web;

import com.company.conferenceroombooking.TestData;
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
import static org.mockito.ArgumentMatchers.anyString;
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
        conferenceRoomResponseDtos = TestData.CONFERENCE_ROOM_RESPONSE_DTO_LIST.get();
    }

    @Test
    public void testGetAvailableConferenceRooms() {
        lenient().when(conferenceRoomService.getAvailableConferenceRooms(anyString(), anyString())).thenReturn(conferenceRoomResponseDtos);
        ResponseEntity<ResponseDto> response = conferenceRoomController.getAvailableConferenceRooms("01:00", "02:00");
        assertNotNull(response);
        assertNotNull(response.getBody());
    }
}
