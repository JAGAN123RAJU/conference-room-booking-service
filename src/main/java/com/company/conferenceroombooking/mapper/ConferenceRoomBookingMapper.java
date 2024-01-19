package com.company.conferenceroombooking.mapper;

import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConferenceRoomBookingMapper implements BiFunction<ConferenceRoomBookingRequestDto, ConferenceRoom, ConferenceRoomBooking> {
    private final ConferenceModelEntityMapper conferenceModelEntityMapper;

    @Override
    public ConferenceRoomBooking apply(ConferenceRoomBookingRequestDto conferenceRoomBookingRequestDto, ConferenceRoom conferenceRoom) {
        return conferenceModelEntityMapper.mapToConferenceRoomBooking(conferenceRoomBookingRequestDto, conferenceRoom);
    }
}
