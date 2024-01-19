package com.company.conferenceroombooking.mapper;

import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceModelEntityMapperTest {

    @InjectMocks
    ConferenceModelEntityMapper mapper;

    List<ConferenceRoom> conferenceRooms;

    ConferenceRoomBookingRequestDto bookingRequestDto;

    ConferenceRoom conferenceRoom;

    ConferenceRoomBooking conferenceRoomBooking;

    @Before
    public void setUp() {
        ConferenceRoom conferenceRoom1 = new ConferenceRoom();
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
        conferenceRooms.addAll(Arrays.asList(conferenceRoom1, conferenceRoom2, conferenceRoom3, conferenceRoom4));

        bookingRequestDto = ConferenceRoomBookingRequestDto.builder().
                participantCount(1).
                startTime("01:00").
                endTime("02:00").
                empEmailId("jaganm@mashreq.com").
                meetingDate("16/04/2023").
                build();

        conferenceRoom = new ConferenceRoom();
        conferenceRoom.setId(1);
        conferenceRoom.setCapacity(3);
        conferenceRoom.setName("Amaze");

        conferenceRoomBooking = new ConferenceRoomBooking();
        conferenceRoomBooking.setStartTime(LocalTime.MIN);
        conferenceRoomBooking.setEndTime(LocalTime.NOON);
        conferenceRoomBooking.setConferenceRoom(conferenceRoom);
        conferenceRoomBooking.setScheduledDate(LocalDate.now());
        conferenceRoomBooking.setId(1);
        conferenceRoomBooking.setParticipantCount(2);
        conferenceRoomBooking.setEmpEmailId("jaganm@mashreq.com");
    }

    @Test
    public void toConferenceRoomDtos(){
        List<ConferenceRoomResponseDto>  responseDtoList= mapper.maptoConferenceRoomResponse(conferenceRooms);
        assertNotNull(responseDtoList);
        assertTrue(!responseDtoList.isEmpty());
    }

    @Test
    public void  toConferenceBookingEntity(){
        ConferenceRoomBooking booking = mapper.mapToConferenceRoomBooking(bookingRequestDto, conferenceRoom);
        assertNotNull(booking);
        assertTrue(booking.getParticipantCount().equals(1));
    }

    @Test
    public void  toBookingResponseDto(){
        ConferenceRoomBookingResponseDto  responseDto = mapper.mapToBookingResponse(conferenceRoomBooking);
        assertNotNull(responseDto);
        assertTrue(responseDto.getRoomName().equals("Amaze"));
    }
}
