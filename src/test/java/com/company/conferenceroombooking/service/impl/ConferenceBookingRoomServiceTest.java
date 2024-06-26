package com.company.conferenceroombooking.service.impl;

import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import com.company.conferenceroombooking.mapper.ConferenceRoomBookingMapper;
import com.company.conferenceroombooking.repository.ConferenceRoomBookingRepository;
import com.company.conferenceroombooking.service.helper.ConferenceBookingServiceHelper;
import com.company.conferenceroombooking.service.helper.ConferenceRoomServiceHelper;
import com.company.conferenceroombooking.validator.BookingRequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceBookingRoomServiceTest {

    @InjectMocks
    ConferenceRoomBookingServiceImpl bookingService;
    @Mock
    ConferenceRoomServiceHelper roomServiceHelper;
    @Mock
    ConferenceRoomBookingRepository roomBookingRepository;
    @Mock
    ConferenceBookingServiceHelper bookingServiceHelper;
    @Mock
    ConferenceRoomBookingMapper mapper;

    @Mock
    BookingRequestValidator validator;

    List<ConferenceRoomResponseDto> conferenceRoomResponseDtos;

    ConferenceRoomBookingRequestDto bookingRequest;

    List<ConferenceRoom> conferenceRooms;

    ConferenceRoomBookingResponseDto responseDto;

    ConferenceRoom conferenceRoom;
    ConferenceRoomBooking roomBooking;


    @Before
    public void setUp() {
        responseDto = ConferenceRoomBookingResponseDto.builder()
                .conferenceBookingId("1").capacity(3).roomName("Amaze")
                .build();
        roomBooking = new ConferenceRoomBooking();
        roomBooking.setStartTime(LocalTime.MIN);
        roomBooking.setEndTime(LocalTime.NOON);
        roomBooking.setConferenceRoom(conferenceRoom);
        roomBooking.setScheduledDate(LocalDate.now());
        roomBooking.setId(1);
        roomBooking.setParticipantCount(2);
        roomBooking.setEmpEmailId("jaganm@mashreq.com");
        conferenceRoom = new ConferenceRoom();
        conferenceRoom.setId(1);
        conferenceRoom.setCapacity(3);
        conferenceRoom.setName("Amaze");
        conferenceRoomResponseDtos = new ArrayList<>();
        ConferenceRoomResponseDto responseDto1 = ConferenceRoomResponseDto.builder().id(1).capacity(3).name("Amaze").build();
        ConferenceRoomResponseDto responseDto2 = ConferenceRoomResponseDto.builder().id(2).capacity(7).name("Beauty").build();
        ConferenceRoomResponseDto responseDto3 = ConferenceRoomResponseDto.builder().id(3).capacity(12).name("Inspire").build();
        ConferenceRoomResponseDto responseDto4 = ConferenceRoomResponseDto.builder().id(4).capacity(20).name("Strive").build();
        conferenceRoomResponseDtos.addAll(Arrays.asList(responseDto1, responseDto2, responseDto3, responseDto4));

        bookingRequest = ConferenceRoomBookingRequestDto.builder().
                participantCount(1).
                startTime("01:00").
                endTime("02:00").
                empEmailId("jaganm@mashreq.com").
                meetingDate("16/04/2023").
                build();

        ConferenceRoom conferenceRoom1= new ConferenceRoom();
        conferenceRoom1.setId(1);
        conferenceRoom1.setCapacity(3);
        conferenceRoom1.setName("Amaze");
        ConferenceRoom conferenceRoom2 = new ConferenceRoom();
        conferenceRoom2.setId(2);
        conferenceRoom2.setCapacity(7);
        conferenceRoom2.setName("Beauty");
        ConferenceRoom conferenceRoom3 = new ConferenceRoom();
        conferenceRoom3.setId(3);
        conferenceRoom3.setCapacity(12);
        conferenceRoom3.setName("Inspire");
        ConferenceRoom conferenceRoom4 = new ConferenceRoom();
        conferenceRoom4.setId(4);
        conferenceRoom4.setCapacity(20);
        conferenceRoom4.setName("Strive");
        conferenceRooms = new ArrayList<>();
        conferenceRooms.addAll(Arrays.asList(conferenceRoom1,conferenceRoom2,conferenceRoom3,conferenceRoom4));
    }

    @Test
    public void testBookAConferenceRoom() {
        lenient().when(roomServiceHelper.getAvailableConferenceRoomForBooking( any())).thenReturn(conferenceRoom);
        lenient().when(mapper.apply(any(), any())).thenReturn(roomBooking);
        lenient().when(roomBookingRepository.save(any())).thenReturn(roomBooking);
        lenient().when(bookingServiceHelper.processResponse(any())).thenReturn(responseDto);

        ConferenceRoomBookingResponseDto response = bookingService.bookConferenceRoom(bookingRequest);
        assertNotNull(response);
        assertTrue(response.getRoomName().equalsIgnoreCase("Amaze"));
    }
}
