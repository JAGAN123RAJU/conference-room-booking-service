package com.company.conferenceroombooking.service.impl;

import com.company.conferenceroombooking.constants.ConferenceRoomBookingConstants;
import com.company.conferenceroombooking.domain.request.ConferenceRoomBookingRequestDto;
import com.company.conferenceroombooking.domain.response.ConferenceRoomBookingResponseDto;
import com.company.conferenceroombooking.entity.ConferenceRoom;
import com.company.conferenceroombooking.entity.ConferenceRoomBooking;
import com.company.conferenceroombooking.mapper.ConferenceRoomBookingMapper;
import com.company.conferenceroombooking.repository.ConferenceRoomBookingRepository;
import com.company.conferenceroombooking.service.ConferenceRoomBookingService;
import com.company.conferenceroombooking.service.helper.ConferenceBookingServiceHelper;
import com.company.conferenceroombooking.service.helper.ConferenceRoomServiceHelper;
import com.company.conferenceroombooking.util.DateUtils;
import com.company.conferenceroombooking.validator.BookingRequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class ConferenceRoomBookingServiceImpl implements ConferenceRoomBookingService {

    private final ConferenceRoomBookingRepository conferenceRoomBookingRepository;

    private BookingRequestValidator requestValidator;

    private ConferenceRoomServiceHelper conferenceRoomServiceHelper;

    private ConferenceRoomBookingMapper conferenceRoomBookingMapper;

    private ConferenceBookingServiceHelper conferenceBookingServiceHelper;

    /**
     * This method is used to book the conference room based on the time slots and the
     * @param bookingRequest
     * @return
     */
    @Override
    public ConferenceRoomBookingResponseDto bookConferenceRoom(ConferenceRoomBookingRequestDto bookingRequest) {
        requestValidator.accept(bookingRequest);
        ConferenceRoom availableConferenceRoom = conferenceRoomServiceHelper.getAvailableConferenceRoomForBooking(bookingRequest);
        ConferenceRoomBooking conferenceRoomBooking = conferenceRoomBookingMapper.apply(bookingRequest, availableConferenceRoom);
        conferenceRoomBooking.setBookingRefNumber(generateBookingId());
        ConferenceRoomBooking conferenceRoomBookingEntity = conferenceRoomBookingRepository.save(conferenceRoomBooking);
        return conferenceBookingServiceHelper.processResponse(conferenceRoomBookingEntity);
    }

    public String generateBookingId() {
        BigDecimal sequenceNo = BigDecimal.ONE.add(conferenceRoomBookingRepository.getMaxBookingId() != null ? conferenceRoomBookingRepository.getMaxBookingId() : BigDecimal.ZERO);
        StringBuilder stringBuilder  = new StringBuilder();
        String value = DateUtils.getDateInString(DateUtils.DDMMYY);
        stringBuilder.append(value);
        value = StringUtils.leftPad(String.valueOf(sequenceNo), ConferenceRoomBookingConstants.PADDING_LENGTH, ConferenceRoomBookingConstants.PADDING_CHAR);
        stringBuilder.append(value);
        return stringBuilder.toString();
    }


}


