package com.company.conferenceroombooking.domain.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TimeSlotMasterDataResponseDto {

    private List<String> slots;
}
