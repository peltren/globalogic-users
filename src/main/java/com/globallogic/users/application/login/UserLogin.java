package com.globallogic.users.application.login;

import com.globallogic.users.application.login.dto.UserLoginMapper;
import com.globallogic.users.application.login.dto.UserLoginResponse;
import com.globallogic.users.domain.exceptions.UserNotFoundException;
import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.output.TokenCreatorOutputPort;
import com.globallogic.users.domain.output.UserByIdOutputPort;
import com.globallogic.users.domain.output.UserLoginOutputPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserLogin {

    private final UserByIdOutputPort userByIdOutputPort;

    private final TokenCreatorOutputPort tokenCreatorOutputPort;

    private final UserLoginOutputPort userLoginOutputPort;

    private final UserLoginMapper mapper;
    
    public UserLoginResponse invoke(String userId) {

        User foundUser = userByIdOutputPort.invoke(UUID.fromString(userId)).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        User loggedUser = userLoginOutputPort.invoke(foundUser.getId());

        String token = tokenCreatorOutputPort.createToken(loggedUser);

        return mapper.toResponse(loggedUser, token);
    }
}
