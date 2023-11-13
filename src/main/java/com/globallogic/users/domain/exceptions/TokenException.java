package com.globallogic.users.domain.exceptions;

public class TokenException extends RuntimeException {
    public TokenException() {
        super("Error en JSON Web Token");
    }
}
