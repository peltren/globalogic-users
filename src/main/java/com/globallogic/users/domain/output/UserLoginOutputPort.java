package com.globallogic.users.domain.output;

import com.globallogic.users.domain.models.User;

import java.util.UUID;

public interface UserLoginOutputPort {

    User invoke(UUID userId);
}
