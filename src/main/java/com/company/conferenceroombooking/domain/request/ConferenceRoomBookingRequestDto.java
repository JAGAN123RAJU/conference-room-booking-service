package com.company.conferenceroombooking.domain.request;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConferenceRoomBookingRequestDto {

    private long conferenceRoomId;

    //@NotNull
    @Pattern(regexp = ConferenceRoomBookingConstants.EMAIL_PATTERN, message = "Provide a valid emailId")
    private String empEmailId;

    @NotNull
    @Min(value = 1, message = "participantCount should be more than 1 to book a conference room")
    private Integer participantCount;

    @NotNull
    @Pattern(regexp = ConferenceRoomBookingConstants.DATE_PATTERN, message = "Provide a valid meetingDate in dd/MM/yyyy format")
    private String meetingDate;

    @NotNull
    @Pattern(regexp = ConferenceRoomBookingConstants.TIME_SLOT_PATTERN, message = "Provide a valid selectedTimeSlot")
    private String startTime;

    @NotNull
    @Pattern(regexp = ConferenceRoomBookingConstants.TIME_SLOT_PATTERN, message = "Provide a valid selectedTimeSlot")
    private String endTime;

}
