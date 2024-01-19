package com.company.conferenceroombooking.mapper;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConferenceModelEntityMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER);
    public List<ConferenceRoomResponseDto> maptoConferenceRoomResponse(List<ConferenceRoom> conferenceRooms) {
        return conferenceRooms.stream().map(this::toConferenceRoomDto).collect(Collectors.toList());
    }

    private ConferenceRoomResponseDto toConferenceRoomDto(ConferenceRoom room) {
        return ConferenceRoomResponseDto.builder().id(room.getId()).capacity(room.getCapacity()).name(room.getName()).build();
    }


    public ConferenceRoomBooking mapToConferenceRoomBooking(ConferenceRoomBookingRequestDto bookingRequest, ConferenceRoom conferenceRoom) {
        return ConferenceRoomBooking.builder()
                .conferenceRoom(conferenceRoom)
                .empEmailId(bookingRequest.getEmpEmailId())
                .participantCount(bookingRequest.getParticipantCount())
                .scheduledDate(LocalDate.parse(bookingRequest.getMeetingDate(), DATE_TIME_FORMATTER))
                .startTime(LocalTime.parse(bookingRequest.getStartTime(), TIME_FORMATTER))
                .endTime(LocalTime.parse(bookingRequest.getEndTime(), TIME_FORMATTER))
                .build();
    }

    public ConferenceRoomBookingResponseDto mapToBookingResponse(ConferenceRoomBooking conferenceRoomBookingEntity) {
        ConferenceRoom room = conferenceRoomBookingEntity.getConferenceRoom();
        return ConferenceRoomBookingResponseDto.builder()
                .conferenceBookingId(conferenceRoomBookingEntity.getBookingRefNumber())
                .roomName(room.getName())
                .capacity(room.getCapacity())
                .build();
    }


}
