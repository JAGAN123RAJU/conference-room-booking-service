package com.company.conferenceroombooking.service;

import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;

public interface ConferenceRoomBookingService {

    ConferenceRoomBookingResponseDto bookConferenceRoom(ConferenceRoomBookingRequestDto request);

}
