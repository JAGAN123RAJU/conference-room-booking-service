package com.company.conferenceroombooking.exception;

import com.company.conferenceroombooking.exception.common.DomainException;

public class ConferenceRoomException extends DomainException {

    public ConferenceRoomException(String message) {
        super(message);
    }

    public ConferenceRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}
