package com.company.conferenceroombooking.exception;

import com.company.conferenceroombooking.exception.common.ErrorDTO;
import com.company.conferenceroombooking.exception.common.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ConferenceRoomBookingExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {ConferenceRoomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ConferenceRoomException conferenceRoomException) {
        log.error(conferenceRoomException.getMessage(), conferenceRoomException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(conferenceRoomException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ConferenceRoomNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(ConferenceRoomNotFoundException conferenceRoomNotFoundException) {
        log.error(conferenceRoomNotFoundException.getMessage(), conferenceRoomNotFoundException);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(conferenceRoomNotFoundException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ConferenceRoomBookingRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ConferenceRoomBookingRequestException conferenceRoomBookingRequestException) {
        log.error(conferenceRoomBookingRequestException.getMessage(), conferenceRoomBookingRequestException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(conferenceRoomBookingRequestException.getMessage())
                .build();
    }
}
