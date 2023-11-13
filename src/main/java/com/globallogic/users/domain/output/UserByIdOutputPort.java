package com.globallogic.users.domain.output;

import com.globallogic.users.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserByIdOutputPort {
    Optional<User> invoke(UUID id);
}
