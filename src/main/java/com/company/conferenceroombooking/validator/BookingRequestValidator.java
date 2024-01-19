package com.company.conferenceroombooking.validator;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.enums.ConferenceRoomBookingErrorCodes;
import com.company.conferenceroombooking.exception.ConferenceRoomBookingRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class BookingRequestValidator implements Consumer<ConferenceRoomBookingRequestDto> {

    @Value("${booking.validation.currentDay.enable}")
    public static boolean enableCurrentDayValidation;

    private static boolean ENABLE_VALIDATION;
    @Value("${booking.validation.currentDay.enable}")
    public void setEnableValidationStatic(boolean enableCurrentDayValidation){
        BookingRequestValidator.ENABLE_VALIDATION = enableCurrentDayValidation;
    }
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER);

    private static final Predicate<ConferenceRoomBookingRequestDto> IS_INVALID_MEETING_DAY =
            (bookingRequest) -> (isInvalidMeetingDate(bookingRequest.getMeetingDate()));

    private static final Predicate<ConferenceRoomBookingRequestDto> IS_INVALID_TIME_SLOT =
            (bookingRequest) -> (isInvalidTimeSlot(bookingRequest.getStartTime(), bookingRequest.getEndTime()));


    @Override
    public void accept(ConferenceRoomBookingRequestDto bookingRequest) {

        if (bookingRequest.getParticipantCount() < 2) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.INVALID_PARTICIPANT_COUNT.getMessage());
        }

        if (IS_INVALID_MEETING_DAY.test(bookingRequest)) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.INVALID_MEETING_DAY.getMessage());
        }
        if (IS_INVALID_TIME_SLOT.test(bookingRequest)) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.INVALID_TIME_SLOT.getMessage());
        }
        if(BookingRequestValidator.isTimeSlotBeforeCurrentTime(bookingRequest.getStartTime(), bookingRequest.getEndTime())) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.PAST_TIME_SLOT_EXCEPTION.getMessage());
        }

    }

    private static boolean isInvalidMeetingDate(String meetingDate) {
        if (ObjectUtils.isEmpty(meetingDate)) {
            return true;
        }
        LocalDate parsedMeetingDate = LocalDate.parse(meetingDate, DATE_TIME_FORMATTER);
        LocalDate currentDate = LocalDate.parse(LocalDateTime.now().format(DATE_TIME_FORMATTER), DATE_TIME_FORMATTER);
        if (ENABLE_VALIDATION) {
            return parsedMeetingDate.compareTo(currentDate) != ConferenceRoomBookingConstants.ZERO ;
        }
        return parsedMeetingDate.compareTo(currentDate) < ConferenceRoomBookingConstants.ZERO ;
    }

     public static boolean isInvalidTimeSlot(String startTime, String endTime) {
        LocalTime startSlot = LocalTime.parse(startTime, TIME_FORMATTER);
        LocalTime endSlot = LocalTime.parse(endTime, TIME_FORMATTER);
        LocalDateTime startDate = startSlot.atDate(LocalDate.now());
        LocalDateTime endDate = endSlot.atDate(LocalDate.now());
        return (startSlot.compareTo(endSlot) > ConferenceRoomBookingConstants.ZERO
                || ChronoUnit.DAYS.between(endDate, startDate) > ConferenceRoomBookingConstants.ZERO);
    }

    public static boolean isTimeSlotBeforeCurrentTime(String startTime, String endTime) {
        LocalTime startSlot = LocalTime.parse(startTime, TIME_FORMATTER);
        LocalTime endSlot = LocalTime.parse(endTime, TIME_FORMATTER);
        LocalDateTime startDate = startSlot.atDate(LocalDate.now());
        LocalDateTime endDate = endSlot.atDate(LocalDate.now());
        return (startDate.compareTo(LocalDateTime.now()) < ConferenceRoomBookingConstants.ZERO || endDate.compareTo(LocalDateTime.now()) < ConferenceRoomBookingConstants.ZERO);
    }
}
