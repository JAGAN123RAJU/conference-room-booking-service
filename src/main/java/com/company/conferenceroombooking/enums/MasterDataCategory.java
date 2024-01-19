package com.company.conferenceroombooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MasterDataCategory {
    START_SLOT("START_SLOT"),
    END_SLOT("END_SLOT");
    String name;
}
