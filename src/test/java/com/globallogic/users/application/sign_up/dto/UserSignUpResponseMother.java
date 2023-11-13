package com.globallogic.users.application.sign_up.dto;

import com.globallogic.users.domain.models.User;

public class UserSignUpResponseMother {

    public static UserSignUpResponse fromUser(User user, String token) {
        return new UserSignUpResponse(
                user.getId(),
                user.getCreated(),
                user.getLastLogin(),
                token,
                user.getIsActive()
        );
    }
}
