package com.company.conferenceroombooking.domain.request;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeSlotDto {

    @NotNull
    @Pattern(regexp = ConferenceRoomBookingConstants.TIME_SLOT_PATTERN, message = "Provide a valid selectedTimeSlot")
    private String startTime;

    @NotNull
    @Pattern(regexp = ConferenceRoomBookingConstants.TIME_SLOT_PATTERN, message = "Provide a valid selectedTimeSlot")
    private String endTime;

    private int interval;
}
