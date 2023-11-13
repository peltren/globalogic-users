package com.globallogic.users.domain.models;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class User {
    UUID id;
    LocalDateTime created;
    LocalDateTime lastLogin;
    Boolean isActive;
    String name;
    String email;
    String password;
    List<UserPhone> phones;
}
