package com.company.conferenceroombooking.validator;


import com.company.conferenceroombooking.TestData;
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
        bookingRequestValidator.accept(TestData.CONFERENCE_ROOM_BOOKING_REQUEST_POSITIVE_SCENARIO.get());
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptExceptionWithOnlyOneParticipant() {
        bookingRequestValidator.accept(TestData.CONFERENCE_ROOM_BOOKING_REQUEST_WITH_ONE_PARTICIPANT_SCENARIO.get());
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptExceptionWithDateLaterThanCurrent() {
        bookingRequestValidator.accept(TestData.CONFERENCE_ROOM_BOOKING_REQUEST_WITH_DATE_LATER_THAN_CURRENT_SCENARIO.get());
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptExceptionWithStartTimeGreater() {
        bookingRequestValidator.accept(TestData.CONFERENCE_ROOM_BOOKING_REQUEST_WITH_START_TIME_GREATER_SCENARIO.get());
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptExceptionWithTimeSlotExceepingToNextDay() {
        bookingRequestValidator.accept(TestData.CONFERENCE_ROOM_BOOKING_REQUEST_WITH_TIME_SLOT_EXCEEDING_TO_NEXT_DAY_SCENARIO.get());
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptWithoutDateTime() {
        bookingRequestValidator.accept(TestData.CONFERENCE_ROOM_BOOKING_REQUEST_WITHOUT_DATE_TIME_SCENARIO.get());
    }

    @Test(expected = ConferenceRoomBookingRequestException.class)
    public void testAcceptExceptionWithTimeSlotBeforeCurrentTimeException() {
        bookingRequestValidator.accept(TestData.CONFERENCE_ROOM_BOOKING_REQUEST_WITH_TIMESLOT_BEFORE_CURRENT_TIME_SCENARIO.get());
    }


}