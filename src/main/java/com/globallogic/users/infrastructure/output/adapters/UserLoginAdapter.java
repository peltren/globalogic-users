package com.globallogic.users.infrastructure.output.adapters;

import com.globallogic.users.domain.exceptions.UserNotFoundException;
import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.output.UserLoginOutputPort;
import com.globallogic.users.infrastructure.output.UserEntityMapper;
import com.globallogic.users.infrastructure.output.repositories.UserEntity;
import com.globallogic.users.infrastructure.output.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserLoginAdapter implements UserLoginOutputPort {

    private final UserRepository repository;

    private final UserEntityMapper mapper;

    @Override
    public User invoke(UUID userId) {

        UserEntity entity = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));

        entity.setLastLogin(LocalDateTime.now());

        entity = repository.save(entity);

        return mapper.toDomain(entity);
    }
}
