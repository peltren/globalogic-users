package com.globallogic.users.domain.output;

import com.globallogic.users.domain.models.User;

public interface TokenCreatorOutputPort {
    String createToken(User user);
}
