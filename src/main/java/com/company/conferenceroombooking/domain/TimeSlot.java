package com.company.conferenceroombooking.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Builder
@Data
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
}
