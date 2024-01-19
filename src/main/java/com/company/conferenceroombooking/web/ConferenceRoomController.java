package com.company.conferenceroombooking.web;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.response.ResponseDto;
import com.company.conferenceroombooking.enums.ResponseStatus;
import com.company.conferenceroombooking.service.ConferenceRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ConferenceRoomBookingConstants.ROOM_BASE_URL)
public class ConferenceRoomController {

    private final ConferenceRoomService conferenceRoomService;

    @Operation(summary = "To get the available conference rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If the Conference Rooms are available"),
            @ApiResponse(responseCode = "400", description = "If request is invalid"),
            @ApiResponse(responseCode = "500", description = "Something went wrong on server's side")
    })
    @GetMapping
    public ResponseEntity<ResponseDto> getAvailableConferenceRooms(@NotBlank @Pattern(regexp = ConferenceRoomBookingConstants.TIME_SLOT_PATTERN, message = "Provide a valid selectedTimeSlot")
                                                                   @RequestParam("startTime") @Parameter String startTime,
                                                                   @NotBlank @Pattern(regexp = ConferenceRoomBookingConstants.TIME_SLOT_PATTERN, message = "Provide a valid selectedTimeSlot")
                                                                   @RequestParam("endTime") @Parameter String endTime) {
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(ResponseStatus.SUCCESS)
                        .data(conferenceRoomService.getAvailableConferenceRooms(startTime, endTime))
                        .build());
    }

}
