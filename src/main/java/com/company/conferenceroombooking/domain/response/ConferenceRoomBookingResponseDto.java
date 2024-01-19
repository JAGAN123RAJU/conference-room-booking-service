package com.company.conferenceroombooking.domain.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConferenceRoomBookingResponseDto {
    private String conferenceBookingId;
    private String roomName;
    private int capacity;
}
