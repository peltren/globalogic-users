package com.globallogic.users.domain.models;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    UNKNOWN_ERROR(1),
    USER_NOT_FOUND_ERROR(2),
    DUPLICATE_EMAIL_ERROR(3),
    BAD_REQUEST_ERROR(4),
    TOKEN_ERROR(5),
    BAD_CREDENTIALS_ERROR(6);

    private final int codigo;

    ErrorCodes(int codigo){
        this.codigo = codigo;
    }
}
