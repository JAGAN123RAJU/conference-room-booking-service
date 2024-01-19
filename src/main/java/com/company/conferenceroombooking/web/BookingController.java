package com.company.conferenceroombooking.web;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ResponseDto;
import com.company.conferenceroombooking.enums.ResponseStatus;
import com.company.conferenceroombooking.service.ConferenceRoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = ConferenceRoomBookingConstants.BOOKING_BASE_URL)
public class BookingController {

    private final ConferenceRoomBookingService conferenceRoomBookingService;

    @Operation(summary = "To book a conference room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If the booking is successful"),
            @ApiResponse(responseCode = "400", description = "If request is invalid"),
            @ApiResponse(responseCode = "500", description = "Something went wrong on server's side")
    })
    @PostMapping
    public ResponseEntity<ResponseDto> bookConferenceRoom(@Valid @RequestBody @Parameter ConferenceRoomBookingRequestDto bookingRequest) {

        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(ResponseStatus.SUCCESS)
                        .data(conferenceRoomBookingService.bookConferenceRoom(bookingRequest))
                        .build());
    }
}
