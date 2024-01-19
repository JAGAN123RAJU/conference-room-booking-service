package com.company.conferenceroombooking.constants;

public class ConferenceRoomBookingConstants {

    public static final String DATE_PATTERN = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";

    public static final String TIME_SLOT_PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public static final String EXCEPTION_MSG_FORMAT = "Exception occurred: {}";

    public static final String EMAIL_PATTERN = "^.+@.+\\..+$";

    public static final String DATE_FORMATTER = "dd/MM/yyyy";

    public static final String TIME_FORMATTER = "HH:mm";

    public static final String BOOKING_BASE_URL = "/api/bookings" ;

    public static final String ROOM_BASE_URL = "/api/rooms";

    public static final int  ZERO = 0;

    public static final int ONE = 1;

    public final static Integer PADDING_LENGTH = 5;
    public final static String  PADDING_CHAR = "0";
}
