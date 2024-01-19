package com.company.conferenceroombooking.domain.response;

import com.company.conferenceroombooking.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ResponseDto<T> {
    private ResponseStatus status;
    private String message;
    private T data;
    private String errorCode;
    private String errorDetails;
    private String uriPath;
}
