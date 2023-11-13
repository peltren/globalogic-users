package com.globallogic.users.application.sign_up.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class UserSignUpResponse {
    UUID id;
    LocalDateTime created;
    LocalDateTime lastLogin;
    String token;
    Boolean isActive;
}
