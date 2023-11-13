package com.globallogic.users.domain.output;

import com.globallogic.users.domain.models.User;

public interface UserCreatorOutputPort {
    User invoke(User user);
}
