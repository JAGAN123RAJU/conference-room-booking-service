package com.company.conferenceroombooking.exception.common;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.exception.ConferenceRoomBookingRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = {ConferenceRoomBookingRequestException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(ConferenceRoomBookingRequestException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ValidationException validationException) {
       ErrorDTO errorDTO;
       if (validationException instanceof ConstraintViolationException) {
           String violations = extractViolationsFromException((ConstraintViolationException) validationException);
           log.error(violations, validationException);
           errorDTO = ErrorDTO.builder()
                   .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                   .message(violations)
                   .build();
       } else {
           String exceptionMessage = validationException.getMessage();
           log.error(exceptionMessage, validationException);
           errorDTO = ErrorDTO.builder()
                   .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                   .message(exceptionMessage)
                   .build();
       }
       return errorDTO;
    }

    private String extractViolationsFromException(ConstraintViolationException validationException) {
        return validationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("--"));
    }



    private <T extends Exception> void logError(T ex) {
        log.error(ConferenceRoomBookingConstants.EXCEPTION_MSG_FORMAT, ex.getMessage());
    }



}
