package com.company.conferenceroombooking.exception.common;

public interface ErrorCode {
    String name();

    default String customErrorCode() {
        return this.name();
    }
}
