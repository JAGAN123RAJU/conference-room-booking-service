package com.company.conferenceroombooking.service.helper;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.TimeSlot;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import com.company.conferenceroombooking.entity.MaintenanceTime;
import com.company.conferenceroombooking.enums.ConferenceRoomBookingErrorCodes;
import com.company.conferenceroombooking.exception.ConferenceRoomBookingRequestException;
import com.company.conferenceroombooking.exception.ConferenceRoomNotFoundException;
import com.company.conferenceroombooking.repository.ConferenceRoomRepository;
import com.company.conferenceroombooking.service.MaintenanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class ConferenceRoomServiceHelper {

    private final ConferenceRoomRepository conferenceRoomRepository;

    private MaintenanceService maintenanceService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER);

    private ConferenceBookingServiceHelper bookingServiceHelper;

    public List<ConferenceRoom> getAllAvailableConferenceRooms(List<ConferenceRoom> conferenceRooms, String startTime, String endTime) {
        validateMaintenanceTimeOverLap(startTime, endTime);
        return filterMaintenanceOverlapRooms(conferenceRooms, startTime, endTime);
    }

    private List<ConferenceRoom> filterMaintenanceOverlapRooms(List<ConferenceRoom> conferenceRooms, String startTime, String endTime) {
        List<ConferenceRoom> availableConferenceRooms = conferenceRooms.stream()
                .filter(conferenceRoom -> isRoomAvailableForRequestedTimeSlot(conferenceRoom, startTime, endTime))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(availableConferenceRooms)) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.CONFERENCE_ROOM_BOOKING_OVERLAP.getMessage());
        }
        return availableConferenceRooms;
    }

    public ConferenceRoom getAvailableConferenceRoomForBooking(ConferenceRoomBookingRequestDto bookingRequestDto) {
        List<ConferenceRoom> validCapacityRooms = validateCapacityToGetConferenceRooms(bookingRequestDto);
        validateMaintenanceTimeOverLap(bookingRequestDto.getStartTime(), bookingRequestDto.getEndTime());
        return validateTimeOverLapToGetAvailableRoom(validCapacityRooms, bookingRequestDto);
    }

    private ConferenceRoom validateTimeOverLapToGetAvailableRoom(List<ConferenceRoom> validCapacityRooms, ConferenceRoomBookingRequestDto bookingRequestDto) {
        return validCapacityRooms.stream()
                .filter(conferenceRoom ->
                        isRoomAvailableForRequestedTimeSlot(conferenceRoom, bookingRequestDto.getStartTime(), bookingRequestDto.getEndTime()))
                .findFirst().orElseThrow(() -> new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.CONFERENCE_ROOM_BOOKING_OVERLAP.getMessage()));
    }

    private List<ConferenceRoom> validateCapacityToGetConferenceRooms(ConferenceRoomBookingRequestDto bookingRequestDto) {
        List<ConferenceRoom> validCapacityRooms = getConferenceRoomsBasedOnCapacity(bookingRequestDto);
        if (CollectionUtils.isEmpty(validCapacityRooms)) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.CONFERENCE_ROOM_INVALID_CAPACITY.getMessage());
        }
        return validCapacityRooms;
    }

    public List<ConferenceRoom> getValidCapacityConferenceRooms(int participantCount) {
        return conferenceRoomRepository.findByCapacityGreaterThanEqualOrderByCapacity(participantCount)
                .orElseThrow(() -> new ConferenceRoomNotFoundException(ConferenceRoomBookingErrorCodes.CONFERENCE_ROOM_NOT_FOUND.getMessage()));
    }

    private boolean isRoomAvailableForRequestedTimeSlot(ConferenceRoom conferenceRoom, String startTime, String endTime) {

        List<ConferenceRoomBooking> conferenceRoomCurrentDayBookings = getExistingBookings(conferenceRoom);
        if (CollectionUtils.isEmpty(conferenceRoomCurrentDayBookings)) {
            return true;
        }
        List<TimeSlot> timeSlots = createBookingTimeSlots(conferenceRoomCurrentDayBookings, startTime, endTime);
        return validateTimeSlotOverLap(timeSlots);// true for no over laps
    }

    private void validateMaintenanceTimeOverLap(String startTime, String endTime) {
        List<MaintenanceTime> maintenanceTimes = maintenanceService.getMaintenanceTimeSlots();
        if (CollectionUtils.isEmpty(maintenanceTimes)) {
            return;
        }
        List<TimeSlot> maintenanceTimeSlots = convertToTimeSlots(maintenanceTimes, startTime, endTime);
        boolean isTimeSlotValid = validateTimeSlotOverLap(maintenanceTimeSlots);//true for no overlaps
        if (!isTimeSlotValid) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.CONFERENCE_ROOM_MAINTENANCE_OVERLAP.getMessage());
        }
    }

    private List<TimeSlot> convertToTimeSlots(List<MaintenanceTime> maintenanceTimeSlots, String startTime, String endTime) {
        List<TimeSlot> timeSlots = maintenanceTimeSlots.stream()
                .map(maintenanceTime -> timeSlotMapper(maintenanceTime.getStartTime(), maintenanceTime.getEndTime()))
                .collect(Collectors.toList());
        timeSlots.add(createTimeSlotForRequestedTime(startTime, endTime));
        return timeSlots;
    }

    private List<TimeSlot> createBookingTimeSlots(List<ConferenceRoomBooking> conferenceRoomCurrentDayBookings, String startTime, String endTime) {
        List<TimeSlot> timeSlots = conferenceRoomCurrentDayBookings.stream()
                .map(previousBooking -> timeSlotMapper(previousBooking.getStartTime(), previousBooking.getEndTime()))
                .collect(Collectors.toList());
        timeSlots.add(createTimeSlotForRequestedTime(startTime, endTime));
        return timeSlots;
    }

    private TimeSlot createTimeSlotForRequestedTime(String startTime, String endTime) {
        return TimeSlot.builder()
                .startTime(LocalTime.parse(startTime, formatter))
                .endTime(LocalTime.parse(endTime, formatter))
                .build();
    }

    private TimeSlot timeSlotMapper(LocalTime startTime, LocalTime endTime) {
        return TimeSlot.builder().startTime(startTime).endTime(endTime).build();
    }

    private boolean validateTimeSlotOverLap(List<TimeSlot> timeSlots) {
        boolean isRequestedTimeValid = Boolean.TRUE;

        if (CollectionUtils.isEmpty(timeSlots)) {
            return Boolean.FALSE;
        }
        timeSlots.sort(Comparator.comparing(TimeSlot::getStartTime));

        int prevPos = ConferenceRoomBookingConstants.ZERO;
        for (int i = ConferenceRoomBookingConstants.ONE; i < timeSlots.size(); i++) {
            int currentPos = i;
            if (timeSlots.get(prevPos).getEndTime().compareTo(timeSlots.get(currentPos).getStartTime()) > 0) {
                isRequestedTimeValid = Boolean.FALSE; // there is an overlap;requested time is invalid
            }
            prevPos = currentPos;
        }
        return isRequestedTimeValid;
    }

    public List<ConferenceRoomBooking> getExistingBookings(ConferenceRoom conferenceRoom) {

        return bookingServiceHelper.getConferenceRoomBookings(conferenceRoom);
    }

    public List<ConferenceRoom> getConferenceRoomsBasedOnCapacity(ConferenceRoomBookingRequestDto requestDto) {
        if (requestDto.getConferenceRoomId() > 0) {
            ConferenceRoom conferenceRoom = conferenceRoomRepository.findByIdAndCapacityGreaterThanEqual(requestDto.getConferenceRoomId(), requestDto.getParticipantCount())
                    .orElseThrow(() -> new ConferenceRoomNotFoundException(ConferenceRoomBookingErrorCodes.CONFERENCE_ROOM_NOT_FOUND.getMessage()));
            return List.of(conferenceRoom);
        } else {
            return getValidCapacityConferenceRooms(requestDto.getParticipantCount());
        }
    }

}
