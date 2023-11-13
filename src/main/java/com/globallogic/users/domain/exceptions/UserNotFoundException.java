package com.globallogic.users.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("User not found for id " + userId);
    }
}
