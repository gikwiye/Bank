package com.steve.bank.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Result {
    private final String objectId;
    private final long modifiedCount;
}
