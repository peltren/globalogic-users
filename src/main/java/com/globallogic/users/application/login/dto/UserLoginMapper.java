package com.globallogic.users.application.login.dto;

import com.globallogic.users.domain.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserLoginMapper {
    UserLoginResponse toResponse(User user, String token);
}
