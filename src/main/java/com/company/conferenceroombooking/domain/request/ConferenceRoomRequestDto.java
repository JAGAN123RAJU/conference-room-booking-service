package com.company.conferenceroombooking.domain.request;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConferenceRoomRequestDto {

    @Pattern(regexp = ConferenceRoomBookingConstants.DATE_PATTERN, message = "Provide a valid meeting Date in mm/dd/yyyy format")
    private String meetingDate;

    @Valid
    @NotNull
    private TimeSlotDto timeSlot;

    @Min(value = 1, message = "Minimum one participant is required to book a conference room")
    private int participantCount;
}
