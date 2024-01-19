package com.company.conferenceroombooking.service.helper;

import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import com.company.conferenceroombooking.entity.MaintenanceTime;
import com.company.conferenceroombooking.mapper.ConferenceModelEntityMapper;
import com.company.conferenceroombooking.repository.ConferenceRoomBookingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceHelperTest {

    @InjectMocks
    ConferenceBookingServiceHelper bookingServiceHelper;

    @Mock
    ConferenceRoomBookingRepository conferenceRoomBookingRepository;

    @Mock
    ConferenceModelEntityMapper mapper;

    List<ConferenceRoomResponseDto> conferenceRoomResponseDtos;

    ConferenceRoomBookingRequestDto bookingRequest;

    List<ConferenceRoom> conferenceRooms;

    ConferenceRoomBookingResponseDto responseDto;

    ConferenceRoom conferenceRoom;
    ConferenceRoomBooking roomBooking;
    MaintenanceTime maintenanceTime;
    List<MaintenanceTime> maintenanceTimes;
    List<ConferenceRoomBooking> conferenceRoomBookingList;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(bookingServiceHelper, "enableCurrentDayValidation", true);
        responseDto = ConferenceRoomBookingResponseDto.builder()
                .conferenceBookingId("1").capacity(3).roomName("Amaze")
                .build();
        maintenanceTimes = new ArrayList<>();
        maintenanceTime = new MaintenanceTime();
        maintenanceTime.setStartTime(LocalTime.of(9,0));
        maintenanceTime.setEndTime(LocalTime.of(9,15));

        conferenceRoom = new ConferenceRoom();
        conferenceRoom.setId(1);
        conferenceRoom.setCapacity(3);
        conferenceRoom.setName("Amaze");

        roomBooking = new ConferenceRoomBooking();
        roomBooking.setStartTime(LocalTime.of(2,15));
        roomBooking.setEndTime(LocalTime.of(2,30));
        roomBooking.setConferenceRoom(conferenceRoom);
        roomBooking.setScheduledDate(LocalDate.now());
        roomBooking.setId(1);
        roomBooking.setParticipantCount(2);
        roomBooking.setEmpEmailId("jaganm@mashreq.com");

        ConferenceRoomBooking roomBooking1 = new ConferenceRoomBooking();
        roomBooking1 = new ConferenceRoomBooking();
        roomBooking1.setStartTime(LocalTime.of(1,0));
        roomBooking1.setEndTime(LocalTime.of(2,0));
        roomBooking1.setConferenceRoom(conferenceRoom);
        roomBooking1.setScheduledDate(LocalDate.now());
        roomBooking1.setId(1);
        roomBooking1.setParticipantCount(2);
        roomBooking1.setEmpEmailId("jaganm@mashreq.com");

        List<ConferenceRoomBooking> conferenceRoomBookings = Arrays.asList(roomBooking1, roomBooking);
        conferenceRoomBookingList = new ArrayList<>();
        conferenceRoomBookingList.addAll(conferenceRoomBookings);


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
    public void testGetConferenceRoomBookingsForCurrentDate() {
         lenient().when(conferenceRoomBookingRepository.findByConferenceRoomAndScheduledDateEquals(conferenceRoom, LocalDate.now())).thenReturn(conferenceRoomBookingList);
         lenient().when(conferenceRoomBookingRepository.findByConferenceRoomAndScheduledDateGreaterThanEqual(conferenceRoom, LocalDate.now())).thenReturn(conferenceRoomBookingList);
        List<ConferenceRoomBooking> conferenceRoomBookings = bookingServiceHelper.getConferenceRoomBookings(conferenceRoom);
        assertNotNull(conferenceRoomBookings);
        assertTrue(conferenceRoomBookings.size()>0);
        assertNotNull(conferenceRoomBookings.get(0).getConferenceRoom());
        assertTrue(conferenceRoomBookings.get(0).getConferenceRoom().getName().equalsIgnoreCase("Amaze"));

    }

    @Test
    public void testGetConferenceRoomBookingsForCurrentDateAndAfter() {
        ReflectionTestUtils.setField(bookingServiceHelper, "enableCurrentDayValidation", false);
        lenient().when(conferenceRoomBookingRepository.findByConferenceRoomAndScheduledDateEquals(conferenceRoom, LocalDate.now())).thenReturn(conferenceRoomBookingList);
        lenient().when(conferenceRoomBookingRepository.findByConferenceRoomAndScheduledDateGreaterThanEqual(conferenceRoom, LocalDate.now())).thenReturn(conferenceRoomBookingList);
        List<ConferenceRoomBooking> conferenceRoomBookings = bookingServiceHelper.getConferenceRoomBookings(conferenceRoom);
        assertNotNull(conferenceRoomBookings);
        assertTrue(conferenceRoomBookings.size()>0);
        assertNotNull(conferenceRoomBookings.get(0).getConferenceRoom());
        assertTrue(conferenceRoomBookings.get(0).getConferenceRoom().getName().equalsIgnoreCase("Amaze"));

    }

    @Test
    public void testCreateBookingResponseDto() {
        lenient().when(mapper.mapToBookingResponse(roomBooking)).thenReturn(responseDto);
        ConferenceRoomBookingResponseDto responseDto = bookingServiceHelper.processResponse(roomBooking);
        assertNotNull(responseDto);
        assertTrue(responseDto.getRoomName().equals("Amaze"));

    }










}
