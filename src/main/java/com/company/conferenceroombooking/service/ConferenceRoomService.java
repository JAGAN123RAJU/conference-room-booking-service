package com.company.conferenceroombooking.service;

import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;

import java.util.List;

public interface ConferenceRoomService {

    List<ConferenceRoomResponseDto> getAvailableConferenceRooms(String startTime, String endTime);
}
