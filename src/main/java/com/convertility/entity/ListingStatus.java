package com.convertility.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ListingStatus {
    NEW("NEW"), PENDING_SELECTION("PENDING SELECTION"), CONFIRMED("CONFIRMED"), COMPLETE("COMPLETE");

    @Getter private final String value;
}
