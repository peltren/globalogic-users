package com.globallogic.users.application.login.dto;

import com.globallogic.users.domain.models.User;

public class UserLoginResponseMother {

    public static UserLoginResponse fromUser(User user, String token) {
        return new UserLoginResponse(
                user.getId(),
                user.getCreated(),
                user.getLastLogin(),
                token,
                user.getIsActive(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhones()
        );
    }
}
