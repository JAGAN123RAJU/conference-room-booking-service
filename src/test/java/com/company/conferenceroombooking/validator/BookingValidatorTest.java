package com.company.conferenceroombooking.validator;


import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.exception.ConferenceRoomBookingRequestException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RunWith(MockitoJUnitRunner.class)
public class BookingValidatorTest {

    @InjectMocks
    BookingRequestValidator bookingRequestValidator;


    @Before
    public void setUp() {


    }

    @Test
    public void testAccept() {
        ConferenceRoomBookingRequestDto bookingRequestDto1 = ConferenceRoomBookingRequestDto.builder()
                .empEmailId("jagan@aamil.com")
                .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
                .startTime(LocalTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER)))
                .endTime(LocalTime.now().plusHours(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER)))
                .participantCount(2)
                .build();
        bookingRequestValidator.accept(bookingRequestDto1);
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptExceptionWithDateLaterThanCurrent() {
        ConferenceRoomBookingRequestDto bookingRequestDto = ConferenceRoomBookingRequestDto.builder()
                .empEmailId("jagan@aamil.com")
                .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
                .startTime("09:00")
                .endTime("10:00")
                .participantCount(1)
                .build();
        bookingRequestDto.setMeetingDate(LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)));
        bookingRequestValidator.accept(bookingRequestDto);
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptExceptionWithStartTimeGreater() {
        ConferenceRoomBookingRequestDto bookingRequestDto = ConferenceRoomBookingRequestDto.builder()
                .empEmailId("jagan@aamil.com")
                .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
                .startTime("09:00")
                .endTime("10:00")
                .participantCount(1)
                .build();
        bookingRequestDto.setStartTime("02:00");
        bookingRequestDto.setEndTime("01:00");
        bookingRequestValidator.accept(bookingRequestDto);
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptExceptionWithTimeSlotExceepingToNextDay() {
        ConferenceRoomBookingRequestDto bookingRequestDto = ConferenceRoomBookingRequestDto.builder()
                .empEmailId("jagan@aamil.com")
                .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
                .startTime("09:00")
                .endTime("10:00")
                .participantCount(1)
                .build();
        bookingRequestDto.setStartTime("23:45");
        bookingRequestDto.setEndTime("00:15");
        bookingRequestValidator.accept(bookingRequestDto);
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptWithoutDateTime() {
        ConferenceRoomBookingRequestDto bookingRequestDto = ConferenceRoomBookingRequestDto.builder()
                .empEmailId("jagan@aamil.com")
                .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
                .startTime("09:00")
                .endTime("10:00")
                .participantCount(1)
                .build();
        bookingRequestDto.setMeetingDate("");
        bookingRequestValidator.accept(bookingRequestDto);
    }

}