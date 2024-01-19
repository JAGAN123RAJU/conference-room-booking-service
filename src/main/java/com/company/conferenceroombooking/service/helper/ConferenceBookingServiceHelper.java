package com.company.conferenceroombooking.service.helper;

import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import com.company.conferenceroombooking.mapper.ConferenceModelEntityMapper;
import com.company.conferenceroombooking.repository.ConferenceRoomBookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConferenceBookingServiceHelper {

    private final ConferenceRoomBookingRepository conferenceRoomBookingRepository;

    private final ConferenceModelEntityMapper mapper;

    @Value("${booking.validation.currentDay.enable}")
    private boolean enableCurrentDayValidation;

    public List<ConferenceRoomBooking> getConferenceRoomBookings(ConferenceRoom conferenceRoom) {
        if ( enableCurrentDayValidation) {
            return conferenceRoomBookingRepository.findByConferenceRoomAndScheduledDateEquals(conferenceRoom, LocalDate.now());
        }
        return conferenceRoomBookingRepository.findByConferenceRoomAndScheduledDateGreaterThanEqual(conferenceRoom, LocalDate.now());

    }

    public ConferenceRoomBookingResponseDto processResponse(ConferenceRoomBooking conferenceRoomBookingEntity) {
        return mapper.mapToBookingResponse(conferenceRoomBookingEntity);
    }
}
