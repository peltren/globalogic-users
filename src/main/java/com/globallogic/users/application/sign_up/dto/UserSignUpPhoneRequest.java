package com.globallogic.users.application.sign_up.dto;

import lombok.Value;

@Value
public class UserSignUpPhoneRequest {
    Long number;
    Integer cityCode;
    String countryCode;
}
