package com.globallogic.users.application.sign_up;

import com.globallogic.shared.RandomStringGenerator;
import com.globallogic.users.application.sign_up.dto.*;
import com.globallogic.users.domain.exceptions.EmailExistsException;
import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.models.UserMother;
import com.globallogic.users.domain.output.TokenCreatorOutputPort;
import com.globallogic.users.domain.output.UserByEmailOutputPort;
import com.globallogic.users.domain.output.UserCreatorOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSignUpTest {

    @Mock
    UserByEmailOutputPort userByEmailOutputPort;

    @Mock
    PasswordEncoder encoder;

    @Mock
    UserCreatorOutputPort userCreatorOutputPort;

    @Mock
    TokenCreatorOutputPort tokenCreatorOutputPort;

    private UserSignUp userSignUp;

    private User user;

    @BeforeEach
    void setup() {
        this.userSignUp = new UserSignUp(userByEmailOutputPort, encoder, userCreatorOutputPort, tokenCreatorOutputPort, new UserSignUpMapperImpl());
        this.user = UserMother.random();
    }

    @Test
    @DisplayName("Should sign up a new User")
    void signUpUser() {

        UserSignUpRequest request = UserSignUpRequestMother.fromUser(user);

        String token = RandomStringGenerator.random(true, 100);

        when(userByEmailOutputPort.invoke(request.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userCreatorOutputPort.invoke(any(User.class))).thenReturn(user);
        when(tokenCreatorOutputPort.createToken(user)).thenReturn(token);

        UserSignUpResponse expectedResult = UserSignUpResponseMother.fromUser(user, token);
        UserSignUpResponse actualResult = userSignUp.invoke(request);

        assertEquals(expectedResult, actualResult);

        verify(userByEmailOutputPort, times(1)).invoke(request.getEmail());
        verify(encoder, times(1)).encode(request.getPassword());
        verify(userCreatorOutputPort, times(1)).invoke(any(User.class));
        verify(tokenCreatorOutputPort, times(1)).createToken(user);
        verifyNoMoreInteractions(userByEmailOutputPort, encoder, userCreatorOutputPort, tokenCreatorOutputPort);
    }

    @Test
    @DisplayName("Should throw UserExistentException")
    void shouldThrowUserExistentException() {

        UserSignUpRequest request = UserSignUpRequestMother.fromUser(user);

        when(userByEmailOutputPort.invoke(request.getEmail())).thenReturn(Optional.of(user));

        EmailExistsException exception = assertThrows(
                EmailExistsException.class,
                () -> userSignUp.invoke(request)
        );

        assertEquals(exception.getMessage(), "Ya existe un usuario con el email: " + request.getEmail());

        verify(userByEmailOutputPort, times(1)).invoke(request.getEmail());
        verifyNoInteractions(encoder, userCreatorOutputPort, tokenCreatorOutputPort);
        verifyNoMoreInteractions(userByEmailOutputPort);
    }
}
