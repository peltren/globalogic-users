package com.globallogic.users.application.login.dto;

import com.globallogic.users.domain.models.UserPhone;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class UserLoginResponse {
    UUID id;
    LocalDateTime created;
    LocalDateTime lastLogin;
    String token;
    Boolean isActive;
    String name;
    String email;
    String password;
    List<UserPhone> phones;
}
