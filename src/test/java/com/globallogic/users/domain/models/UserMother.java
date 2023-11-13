package com.globallogic.users.domain.models;

import com.globallogic.shared.RandomStringGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class UserMother {

    public static User random() {

        List<UserPhone> userPhones = UserPhoneMother.randomList();

        return new User(
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new Random().nextBoolean(),
                RandomStringGenerator.random(false, 20),
                RandomStringGenerator.random(false, 40),
                RandomStringGenerator.random(false, 12),
                userPhones
        );
    }
}
