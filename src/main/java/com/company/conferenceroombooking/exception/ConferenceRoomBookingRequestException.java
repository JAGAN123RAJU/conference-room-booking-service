package com.company.conferenceroombooking.exception;

import com.company.conferenceroombooking.exception.common.DomainException;

public class ConferenceRoomBookingRequestException extends DomainException {

    public ConferenceRoomBookingRequestException(String message) {
        super(message);
    }

    public ConferenceRoomBookingRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
