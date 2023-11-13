package com.globallogic.users.infrastructure.output.adapters;

import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.output.UserCreatorOutputPort;
import com.globallogic.users.infrastructure.output.UserEntityMapper;
import com.globallogic.users.infrastructure.output.repositories.UserEntity;
import com.globallogic.users.infrastructure.output.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserCreatorAdapter implements UserCreatorOutputPort {

    private final UserRepository repository;

    private final UserEntityMapper mapper;

    @Override
    public User invoke(User user) {

        UserEntity entity = repository.save(mapper.toEntity(user));

        return mapper.toDomain(entity);
    }
}
