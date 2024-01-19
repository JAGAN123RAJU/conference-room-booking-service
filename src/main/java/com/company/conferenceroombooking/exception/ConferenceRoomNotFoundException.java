package com.company.conferenceroombooking.exception;

import com.company.conferenceroombooking.exception.common.DomainException;

public class ConferenceRoomNotFoundException extends DomainException {

    public ConferenceRoomNotFoundException(String message) {
        super(message);
    }

    public ConferenceRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
