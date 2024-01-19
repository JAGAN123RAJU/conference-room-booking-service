package com.company.conferenceroombooking.enums;

import com.company.conferenceroombooking.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public enum ConferenceRoomBookingErrorCodes implements ErrorCode {

    INVALID_REQUEST("400", "Invalid Request"),

    INVALID_MEETING_DAY("400", "Invalid Date! Meeting can be set only for current day"),

    INVALID_PARTICIPANT_COUNT("400", "Participant Count should be greater than 1"),

    INVALID_TIME_SLOT("400", "Invalid time slots"),

    PAST_TIME_SLOT_EXCEPTION("400", "Please select up coming tome slots. But not the passed time slots."),

    INVALID_CONFERENCE_ROOM_ID("400", "Invalid Conference Room Id"),

    CONFERENCE_ROOM_NOT_FOUND("400", "Conference Room Not found"),

    CONFERENCE_ROOM_MAINTENANCE_OVERLAP("400", "Selected slot is not available because of some maintenance work"),

    CONFERENCE_ROOM_BOOKING_OVERLAP("400", "None of the Conference Rooms have sufficient seats for booking at specified time. Please try for different time slot."),

    CONFERENCE_ROOM_INVALID_CAPACITY("400", "None of the Conference Rooms have sufficient seats.");

    private String code;
    private String message;

    ConferenceRoomBookingErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getLogStatement(ConferenceRoomBookingErrorCodes bookingErrorCodes) {
        return bookingErrorCodes.getCode() + ":" + bookingErrorCodes.getMessage();
    }

    public static String getLogStatement(ConferenceRoomBookingErrorCodes bookingErrorCodes, String type) {
        return bookingErrorCodes.getCode() + ":" + type + " " + bookingErrorCodes.getMessage();
    }

}
