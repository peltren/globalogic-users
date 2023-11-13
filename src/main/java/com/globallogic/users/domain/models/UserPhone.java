package com.globallogic.users.domain.models;

import lombok.Value;

@Value
public class UserPhone {
    Long number;
    Integer cityCode;
    String countryCode;
}
