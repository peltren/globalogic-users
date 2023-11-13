package com.globallogic.users.infrastructure.output.adapters;

import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.output.UserByEmailOutputPort;
import com.globallogic.users.infrastructure.output.UserEntityMapper;
import com.globallogic.users.infrastructure.output.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserByEmailFinderAdapter implements UserByEmailOutputPort {

    private final UserRepository repository;

    private final UserEntityMapper mapper;

    @Override
    public Optional<User> invoke(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }
}
