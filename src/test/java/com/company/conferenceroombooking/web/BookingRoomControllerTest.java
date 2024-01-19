package com.company.conferenceroombooking.web;

import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.domain.response.ResponseDto;
import com.company.conferenceroombooking.service.ConferenceRoomBookingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class BookingRoomControllerTest {

    @InjectMocks
    BookingController bookingController;
    @Mock
    ConferenceRoomBookingService bookingService;

    List<ConferenceRoomResponseDto> conferenceRoomResponseDtos;

    ConferenceRoomBookingRequestDto conferenceRoomBookingRequestDto;

    ConferenceRoomBookingResponseDto responseDto;


    @Before
    public void setUp() {
        responseDto = ConferenceRoomBookingResponseDto.builder().conferenceBookingId("1").capacity(3).roomName("Amaze").build();
        conferenceRoomBookingRequestDto  = ConferenceRoomBookingRequestDto.builder().
                participantCount(1).
                startTime("01:00").
                endTime("02:00").
                empEmailId("jaganm@mashreq.com").
                meetingDate("16/04/2023").
                build();

    }

    @Test
    public void testGetAvailableConferenceRooms() {
       lenient().when(bookingService.bookConferenceRoom(any())).thenReturn(responseDto);
        ResponseEntity<ResponseDto> response = bookingController.bookConferenceRoom(conferenceRoomBookingRequestDto);
        assertNotNull(response);
        assertNotNull(response.getBody());
    }
}
