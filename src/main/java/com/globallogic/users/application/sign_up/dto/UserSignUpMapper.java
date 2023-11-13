package com.globallogic.users.application.sign_up.dto;

import com.globallogic.users.domain.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserSignUpMapper {
    UserSignUpResponse toResponse(User user, String token);
}
