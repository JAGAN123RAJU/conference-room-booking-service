package com.company.conferenceroombooking.domain.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConferenceRoomResponseDto {

    private long id;
    private String name;
    private int capacity;

}
