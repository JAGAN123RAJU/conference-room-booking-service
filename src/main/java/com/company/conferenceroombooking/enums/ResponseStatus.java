package com.company.conferenceroombooking.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ResponseStatus {
    SUCCESS,
    FAIL,
    ERROR;
    private ResponseStatus() {
    }

    @JsonCreator
    public static ResponseStatus from(String value) {
        return (ResponseStatus) Arrays.asList(values()).stream().filter((item) -> {
            return item.toValue().equalsIgnoreCase(value);
        }).findFirst().orElse( null);
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
