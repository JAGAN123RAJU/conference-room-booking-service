package com.company.conferenceroombooking.service.impl;

import com.company.conferenceroombooking.domain.response.ConferenceRoomResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.enums.ConferenceRoomBookingErrorCodes;
import com.company.conferenceroombooking.exception.ConferenceRoomBookingRequestException;
import com.company.conferenceroombooking.exception.ConferenceRoomNotFoundException;
import com.company.conferenceroombooking.mapper.ConferenceModelEntityMapper;
import com.company.conferenceroombooking.repository.ConferenceRoomRepository;
import com.company.conferenceroombooking.service.ConferenceRoomService;
import com.company.conferenceroombooking.service.helper.ConferenceRoomServiceHelper;
import com.company.conferenceroombooking.validator.BookingRequestValidator;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

    private final ConferenceRoomRepository conferenceRoomRepository;

    private ConferenceModelEntityMapper conferenceModelEntityMapper;

    private ConferenceRoomServiceHelper conferenceRoomServiceHelper;

    @Override
    public List<ConferenceRoomResponseDto> getAvailableConferenceRooms(String startTime, String endTime) {

        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findAll();
        if (CollectionUtils.isEmpty(conferenceRooms)) {
            throw new ConferenceRoomNotFoundException("No Conference Rooms available");
        }
        if (BookingRequestValidator.isInvalidTimeSlot(startTime, endTime)) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.INVALID_TIME_SLOT.getMessage());
        }

        if(BookingRequestValidator.isTimeSlotBeforeCurrentTime(startTime, endTime)) {
            throw new ConferenceRoomBookingRequestException(ConferenceRoomBookingErrorCodes.PAST_TIME_SLOT_EXCEPTION.getMessage());
        }
        List<ConferenceRoom> availableConferenceRooms = conferenceRoomServiceHelper.getAllAvailableConferenceRooms(conferenceRooms, startTime, endTime);
        return conferenceModelEntityMapper.maptoConferenceRoomResponse(availableConferenceRooms);
    }


}
