package com.globallogic.users.infrastructure.output.adapters;

import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.output.UserByIdOutputPort;
import com.globallogic.users.infrastructure.output.UserEntityMapper;
import com.globallogic.users.infrastructure.output.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserByIdFinderAdapter implements UserByIdOutputPort {

    private final UserRepository repository;

    private final UserEntityMapper mapper;

    @Override
    public Optional<User> invoke(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
