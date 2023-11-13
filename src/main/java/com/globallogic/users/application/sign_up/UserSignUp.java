package com.globallogic.users.application.sign_up;

import com.globallogic.users.application.sign_up.dto.UserSignUpRequest;
import com.globallogic.users.application.sign_up.dto.UserSignUpMapper;
import com.globallogic.users.application.sign_up.dto.UserSignUpResponse;
import com.globallogic.users.domain.exceptions.EmailExistsException;
import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.models.UserPhone;
import com.globallogic.users.domain.output.TokenCreatorOutputPort;
import com.globallogic.users.domain.output.UserByEmailOutputPort;
import com.globallogic.users.domain.output.UserCreatorOutputPort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserSignUp {

    private final UserByEmailOutputPort userByEmailOutputPort;

    private final PasswordEncoder encoder;

    private final UserCreatorOutputPort userCreatorOutputPort;

    private final TokenCreatorOutputPort tokenCreatorOutputPort;

    private final UserSignUpMapper mapper;

    public UserSignUpResponse invoke(UserSignUpRequest command) {

        userByEmailOutputPort.invoke(command.getEmail()).ifPresent(
                user -> {
                    throw new EmailExistsException(command.getEmail());
                }
        );

        User user = userCreatorOutputPort.invoke(buildNewUser(command));

        String token = tokenCreatorOutputPort.createToken(user);

        return mapper.toResponse(user, token);
    }

    private User buildNewUser(UserSignUpRequest command) {
        return new User(
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                command.getName(),
                command.getEmail(),
                encoder.encode(command.getPassword()),
                command.getPhones().stream().map(phone -> new UserPhone(
                        phone.getNumber(),
                        phone.getCityCode(),
                        phone.getCountryCode()
                )).collect(Collectors.toList())
        );
    }
}
