package com.company.conferenceroombooking.web;

import com.company.conferenceroombooking.TestData;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ResponseDto;
import com.company.conferenceroombooking.service.ConferenceRoomBookingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class BookingRoomControllerTest {

    @InjectMocks
    BookingController bookingController;
    @Mock
    ConferenceRoomBookingService bookingService;

    ConferenceRoomBookingRequestDto conferenceRoomBookingRequestDto;

    ConferenceRoomBookingResponseDto responseDto;


    @Before
    public void setUp() {
        responseDto = TestData.CONFERENCE_ROOM_BOOKING_RESPONSE_DTO.get();
        conferenceRoomBookingRequestDto  = TestData.CONFERENCE_ROOM_BOOKING_REQUEST_DTO.get();

    }

    @Test
    public void testGetAvailableConferenceRooms() {
       lenient().when(bookingService.bookConferenceRoom(any())).thenReturn(responseDto);
        ResponseEntity<ResponseDto> response = bookingController.bookConferenceRoom(conferenceRoomBookingRequestDto);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
