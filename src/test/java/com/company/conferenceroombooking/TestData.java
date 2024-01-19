package com.company.conferenceroombooking;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import com.company.conferenceroombooking.entity.MaintenanceTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class TestData {

    public static Supplier<ConferenceRoomBookingRequestDto> CONFERENCE_ROOM_BOOKING_REQUEST_DTO = () ->
            ConferenceRoomBookingRequestDto.builder()
                    .participantCount(1)
                    .startTime("01:00")
                    .endTime("02:00")
                    .empEmailId("jaganm@mashreq.com")
                    .meetingDate("16/04/2023")
                    .build();

    public static Supplier<ConferenceRoomBookingResponseDto> CONFERENCE_ROOM_BOOKING_RESPONSE_DTO = () ->

            ConferenceRoomBookingResponseDto.builder()
                    .conferenceBookingId("1")
                    .capacity(3)
                    .roomName("Amaze")
                    .build();

    public static Supplier<List<ConferenceRoomResponseDto>> CONFERENCE_ROOM_RESPONSE_DTO_LIST = () ->

            List.of(ConferenceRoomResponseDto.builder().id(1).capacity(3).name("Amaze").build(),
                    ConferenceRoomResponseDto.builder().id(2).capacity(7).name("Beauty").build(),
                    ConferenceRoomResponseDto.builder().id(3).capacity(12).name("Inspire").build(),
                    ConferenceRoomResponseDto.builder().id(4).capacity(20).name("Strive").build()
            );

    public static Supplier<List<ConferenceRoom>> CONFERENCE_ROOM_DTO_LIST = () ->

            List.of(ConferenceRoom.builder().id(1).capacity(3).name("Amaze").build(),
                    ConferenceRoom.builder().id(2).capacity(7).name("Beauty").build(),
                    ConferenceRoom.builder().id(3).capacity(12).name("Inspire").build(),
                    ConferenceRoom.builder().id(4).capacity(20).name("Strive").build()
            );

    public static Supplier<ConferenceRoomBookingRequestDto> CONFERENCE_ROOM_BOOKING_REQUEST_POSITIVE_SCENARIO = () ->

            ConferenceRoomBookingRequestDto.builder()
            .empEmailId("jagan@gmail.com")
            .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
            .startTime(LocalTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER)))
            .endTime(LocalTime.now().plusHours(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER)))
            .participantCount(2)
            .build();

    public static Supplier<ConferenceRoomBookingRequestDto> CONFERENCE_ROOM_BOOKING_REQUEST_WITH_ONE_PARTICIPANT_SCENARIO = () ->
    ConferenceRoomBookingRequestDto.builder()
            .empEmailId("jagan@gmail.com")
            .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
            .startTime("09:00")
            .endTime("10:00")
            .participantCount(1)
            .build();

    public static Supplier<ConferenceRoomBookingRequestDto> CONFERENCE_ROOM_BOOKING_REQUEST_WITH_DATE_LATER_THAN_CURRENT_SCENARIO = () ->
    ConferenceRoomBookingRequestDto.builder()
            .empEmailId("jagan@gmail.com")
            .meetingDate(LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
            .startTime("09:00")
            .endTime("10:00")
            .participantCount(2)
            .build();

    public static Supplier<ConferenceRoomBookingRequestDto> CONFERENCE_ROOM_BOOKING_REQUEST_WITH_START_TIME_GREATER_SCENARIO = () ->
    ConferenceRoomBookingRequestDto.builder()
            .empEmailId("jagan@gmail.com")
            .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
            .startTime("02:00")
            .endTime("01:00")
            .participantCount(2)
            .build();

    public static Supplier<ConferenceRoomBookingRequestDto> CONFERENCE_ROOM_BOOKING_REQUEST_WITH_TIME_SLOT_EXCEEDING_TO_NEXT_DAY_SCENARIO = () ->
    ConferenceRoomBookingRequestDto.builder()
            .empEmailId("jagan@gmail.com")
            .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
            .startTime("23:45")
            .endTime("00:15")
            .participantCount(2)
            .build();
    public static Supplier<ConferenceRoomBookingRequestDto> CONFERENCE_ROOM_BOOKING_REQUEST_WITHOUT_DATE_TIME_SCENARIO = () ->
    ConferenceRoomBookingRequestDto.builder()
            .empEmailId("jagan@gmail.com")
            .meetingDate("")
            .startTime("09:00")
            .endTime("10:00")
            .participantCount(2)
            .build();

    public static Supplier<ConferenceRoomBookingRequestDto> CONFERENCE_ROOM_BOOKING_REQUEST_WITH_TIMESLOT_BEFORE_CURRENT_TIME_SCENARIO = () ->
    ConferenceRoomBookingRequestDto.builder()
            .empEmailId("jagan@gmail.com")
            .meetingDate(LocalDate.now().format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.DATE_FORMATTER)))
            .startTime(LocalTime.now().minusHours(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER)))
            .endTime(LocalTime.now().plusHours(1).format(DateTimeFormatter.ofPattern(ConferenceRoomBookingConstants.TIME_FORMATTER)))
            .participantCount(2)
            .build();

    public static Supplier<List<MaintenanceTime>> MAINTENANCE_TIME_LIST = () ->
            List.of(MaintenanceTime.builder()
                    .startTime(LocalTime.of(9,00))
                    .endTime(LocalTime.of(9,15))
                    .build(), MaintenanceTime.builder()
                            .startTime(LocalTime.of(15,00))
                            .endTime(LocalTime.of(15,15))
                            .build()
                    );

    public static Supplier<List<ConferenceRoom>> CONFERENCE_ROOM_BOOKING = () ->
            List.of(ConferenceRoom.builder().id(1).capacity(3).name("Amaze").build(),
                    ConferenceRoom.builder().id(2).capacity(7).name("Beauty").build(),
                    ConferenceRoom.builder().id(3).capacity(12).name("Inspire").build(),
                    ConferenceRoom.builder().id(4).capacity(20).name("Strive").build());





}
