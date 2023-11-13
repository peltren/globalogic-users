package com.globallogic.users.domain.output;

import com.globallogic.users.domain.models.User;

import java.util.Optional;

public interface UserByEmailOutputPort {
    Optional<User> invoke(String email);
}
