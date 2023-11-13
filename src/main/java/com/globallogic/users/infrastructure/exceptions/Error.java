package com.globallogic.users.infrastructure.exceptions;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Error {
    LocalDateTime timestamp;
    int codigo;
    String detail;
}
