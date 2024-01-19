package com.company.conferenceroombooking.service.helper;

import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import com.company.conferenceroombooking.entity.MaintenanceTime;
import com.company.conferenceroombooking.repository.ConferenceRoomRepository;
import com.company.conferenceroombooking.service.MaintenanceService;
import com.company.conferenceroombooking.validator.BookingRequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceHelperTest {

    @InjectMocks
    ConferenceRoomServiceHelper roomServiceHelper;

    @Mock
    BookingRequestValidator requestValidator;
    @Mock
    ConferenceBookingServiceHelper bookingServiceHelper;
    @Mock
    ConferenceRoomRepository roomRepository;
    @Mock
    MaintenanceService maintenanceService;

    List<ConferenceRoomResponseDto> conferenceRoomResponseDtos;

    ConferenceRoomBookingRequestDto bookingRequest;

    List<ConferenceRoom> conferenceRooms;

    ConferenceRoomBookingResponseDto responseDto;

    ConferenceRoom conferenceRoom;
    ConferenceRoomBooking roomBooking;
    MaintenanceTime maintenanceTime;
    List<MaintenanceTime> maintenanceTimes;


    @Before
    public void setUp() {
        responseDto = ConferenceRoomBookingResponseDto.builder()
                .conferenceBookingId("1").capacity(3).roomName("Amaze")
                .build();
        maintenanceTimes = new ArrayList<>();
        maintenanceTime = new MaintenanceTime();
        maintenanceTime.setStartTime(LocalTime.of(9, 0));
        maintenanceTime.setEndTime(LocalTime.of(9, 15));

        conferenceRoom = new ConferenceRoom();
        conferenceRoom.setId(1);
        conferenceRoom.setCapacity(3);
        conferenceRoom.setName("Amaze");

        roomBooking = new ConferenceRoomBooking();
        roomBooking.setStartTime(LocalTime.MIN);
        roomBooking.setEndTime(LocalTime.NOON);
        roomBooking.setConferenceRoom(conferenceRoom);
        roomBooking.setScheduledDate(LocalDate.now());
        roomBooking.setId(1);
        roomBooking.setParticipantCount(2);
        roomBooking.setEmpEmailId("jaganm@mashreq.com");

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
    }

    @Test
    public void testGetConferenceRoomsBasedOnIdAndCapacity() {
        bookingRequest.setConferenceRoomId(1);
        lenient().when(roomRepository.findByIdAndCapacityGreaterThanEqual(bookingRequest.getConferenceRoomId()
                , bookingRequest.getParticipantCount())).thenReturn(Optional.of(conferenceRoom));

        List<ConferenceRoom> conferenceRoomList = roomServiceHelper.getConferenceRoomsBasedOnCapacity(bookingRequest);
        assertNotNull(conferenceRoomList);
        assertTrue(conferenceRoomList.size() > 0);
    }

    @Test
    public void testGetConferenceRoomsException() {
        lenient().when(roomRepository.findByIdAndCapacityGreaterThanEqual(bookingRequest.getConferenceRoomId()
                , bookingRequest.getParticipantCount())).thenReturn(null);

        try {
            List<ConferenceRoom> conferenceRoomList = roomServiceHelper.getConferenceRoomsBasedOnCapacity(bookingRequest);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Conference Room Not found"));
        }
    }

    @Test
    public void testGetConferenceRoomsBasedOnCacpacity() {
        bookingRequest.setConferenceRoomId(0);
        lenient().when(roomRepository.findByCapacityGreaterThanEqualOrderByCapacity(bookingRequest.getParticipantCount())).thenReturn(Optional.of(conferenceRooms));
        List<ConferenceRoom> conferenceRoomList = roomServiceHelper.getConferenceRoomsBasedOnCapacity(bookingRequest);
        assertNotNull(conferenceRoomList);
        assertTrue(conferenceRoomList.size() > 0);
    }

    @Test
    public void testGetConferenceRoomsMoreParticipants() {
        bookingRequest.setConferenceRoomId(0);
        bookingRequest.setParticipantCount(21);
        lenient().when(roomRepository.findByCapacityGreaterThanEqualOrderByCapacity(
                bookingRequest.getParticipantCount())).thenReturn(Optional.of(Collections.EMPTY_LIST));
        try {
            List<ConferenceRoom> conferenceRoomList = roomServiceHelper.getConferenceRoomsBasedOnCapacity(bookingRequest);
        } catch (Exception ex) {
            assertTrue(ex.getMessage().contains("Conference Room Not found"));
        }
    }

    @Test
    public void testGetAllAvailableConferenceRooms() {
        bookingRequest.setConferenceRoomId(0);
        lenient().when(maintenanceService.getMaintenanceTimeSlots()).thenReturn(Collections.singletonList(maintenanceTime));
        lenient().when(bookingServiceHelper.getConferenceRoomBookings(conferenceRoom)).thenReturn(Arrays.asList(roomBooking));
        List<ConferenceRoom> conferenceRoomList = roomServiceHelper.getAllAvailableConferenceRooms(conferenceRooms, "01:00", "02:00");
        assertNotNull(conferenceRoomList);
        assertTrue(conferenceRoomList.size() > 0);
    }

    @Test
    public void testGetAvailableConferenceRoomsWithNoMaintenance() {
        bookingRequest.setConferenceRoomId(0);
        lenient().when(maintenanceService.getMaintenanceTimeSlots()).thenReturn(Collections.EMPTY_LIST);
        List<ConferenceRoom> conferenceRoomList = roomServiceHelper.getAllAvailableConferenceRooms(conferenceRooms, "01:00", "02:00");
        assertNotNull(conferenceRoomList);
        assertTrue(conferenceRoomList.size() > 0);
    }

    @Test
    public void testRequestedRoomAvailabilityForBookingExc() {
        bookingRequest.setConferenceRoomId(1);
        lenient().when(roomRepository.findByIdAndCapacityGreaterThanEqual(
                        bookingRequest.getConferenceRoomId(), bookingRequest.getParticipantCount())).
                thenReturn(Optional.of(conferenceRoom));

        lenient().when(maintenanceService.getMaintenanceTimeSlots()).thenReturn(Collections.singletonList(maintenanceTime));
        lenient().when(bookingServiceHelper.getConferenceRoomBookings(conferenceRoom)).thenReturn(Arrays.asList(roomBooking));
        try {
            roomServiceHelper.getAvailableConferenceRoomForBooking(bookingRequest);
        } catch (Exception exc) {
            assertTrue(exc.getMessage().contains("None of the Conference Rooms have sufficient seats for booking at specified time. Please try for different time slot."));
        }

    }

    @Test
    public void testRequestedRoomAvailabilityForBooking() {
        bookingRequest.setConferenceRoomId(1);
        bookingRequest.setStartTime("13:00");
        bookingRequest.setStartTime("14:00");
        lenient().when(roomRepository.findByIdAndCapacityGreaterThanEqual(
                        bookingRequest.getConferenceRoomId(), bookingRequest.getParticipantCount())).
                thenReturn(Optional.of(conferenceRoom));

        lenient().when(maintenanceService.getMaintenanceTimeSlots()).thenReturn(Collections.singletonList(maintenanceTime));
        lenient().when(bookingServiceHelper.getConferenceRoomBookings(conferenceRoom)).thenReturn(Arrays.asList(roomBooking));
        ConferenceRoom conferenceRoom = roomServiceHelper.getAvailableConferenceRoomForBooking(bookingRequest);
        assertNotNull(conferenceRoom);
    }


    @Test
    public void testGetAvailableConferenceRoomForBooking() {
        bookingRequest.setConferenceRoomId(0);
        lenient().when(roomRepository.findByCapacityGreaterThanEqualOrderByCapacity(bookingRequest.getParticipantCount())).
                thenReturn(Optional.of(conferenceRooms));

        lenient().when(maintenanceService.getMaintenanceTimeSlots()).thenReturn(Collections.singletonList(maintenanceTime));
        lenient().when(bookingServiceHelper.getConferenceRoomBookings(conferenceRoom)).thenReturn(Arrays.asList(roomBooking));
        List<ConferenceRoom> conferenceRoomList = roomServiceHelper.getAllAvailableConferenceRooms(conferenceRooms, "01:00", "02:00");
        assertNotNull(conferenceRoomList);
        assertTrue(conferenceRoomList.size() > 0);
    }

    @Test
    public void getAvailableConferenceRoomForBookingCapacityException() {
        bookingRequest.setConferenceRoomId(0);
        lenient().when(roomRepository.findByCapacityGreaterThanEqualOrderByCapacity(bookingRequest.getParticipantCount())).
                thenReturn(null);

        lenient().when(maintenanceService.getMaintenanceTimeSlots()).thenReturn(Collections.singletonList(maintenanceTime));
        lenient().when(bookingServiceHelper.getConferenceRoomBookings(conferenceRoom)).thenReturn(Arrays.asList(roomBooking));
        try {
            List<ConferenceRoom> conferenceRoomList = roomServiceHelper.getAllAvailableConferenceRooms(conferenceRooms, "01:00", "02:00");
        } catch (Exception exc) {
            assertTrue(exc.getMessage().contains("Conference Room Capacity is not sufficient"));
        }
    }
}
