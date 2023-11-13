package com.globallogic.users.application.sign_up.dto;

import com.globallogic.users.domain.models.User;

import java.util.stream.Collectors;

public class UserSignUpRequestMother {

    public static UserSignUpRequest fromUser(User user) {
        return new UserSignUpRequest(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhones().stream().map(phone ->
                        new UserSignUpPhoneRequest(
                                phone.getNumber(),
                                phone.getCityCode(),
                                phone.getCountryCode()
                        )
                ).collect(Collectors.toList())
        );
    }
}
