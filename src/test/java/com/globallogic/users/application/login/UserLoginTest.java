package com.globallogic.users.application.login;

import com.globallogic.shared.RandomStringGenerator;
import com.globallogic.users.application.login.dto.UserLoginMapperImpl;
import com.globallogic.users.application.login.dto.UserLoginResponse;
import com.globallogic.users.application.login.dto.UserLoginResponseMother;
import com.globallogic.users.domain.exceptions.UserNotFoundException;
import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.models.UserMother;
import com.globallogic.users.domain.output.TokenCreatorOutputPort;
import com.globallogic.users.domain.output.UserByIdOutputPort;
import com.globallogic.users.domain.output.UserLoginOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLoginTest {

    @Mock
    UserByIdOutputPort userByIdOutputPort;

    @Mock
    UserLoginOutputPort userLoginOutputPort;

    @Mock
    TokenCreatorOutputPort tokenCreator;

    private UserLogin userLogin;

    @BeforeEach
    void setup() {
        this.userLogin = new UserLogin(userByIdOutputPort, tokenCreator, userLoginOutputPort, new UserLoginMapperImpl());
    }

    @Test
    @DisplayName("Should login a User")
    void invoke() {

        User user = UserMother.random();

        String token = RandomStringGenerator.random(true, 100);

        when(userByIdOutputPort.invoke(user.getId())).thenReturn(Optional.of(user));
        when(tokenCreator.createToken(user)).thenReturn(token);
        when(userLoginOutputPort.invoke(user.getId())).thenReturn(user);

        UserLoginResponse expectedResult = UserLoginResponseMother.fromUser(user, token);
        UserLoginResponse actualResult = userLogin.invoke(user.getId().toString());

        assertEquals(expectedResult, actualResult);

        verify(userByIdOutputPort, times(1)).invoke(user.getId());
        verify(tokenCreator, times(1)).createToken(user);
        verify(userLoginOutputPort, times(1)).invoke(user.getId());
        verifyNoMoreInteractions(userByIdOutputPort, tokenCreator, userLoginOutputPort);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException")
    void shouldThrowUserNotFoundException() {

        UUID userId = UUID.randomUUID();

        when(userByIdOutputPort.invoke(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userLogin.invoke(userId.toString())
        );

        assertEquals(exception.getMessage(), "User not found for id " + userId);

        verify(userByIdOutputPort, times(1)).invoke(userId);
        verifyNoInteractions(tokenCreator, userLoginOutputPort);
        verifyNoMoreInteractions(userByIdOutputPort);
    }
}
