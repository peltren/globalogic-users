package com.globallogic.users.domain.models;

import com.globallogic.shared.RandomStringGenerator;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserPhoneMother {

    private static Random random = new Random();

    public static List<UserPhone> randomList() {
        int listSize = random.nextInt(5);
        return Stream.generate(UserPhoneMother::random).limit(listSize).collect(Collectors.toList());
    }

    private static UserPhone random() {
        return new UserPhone(
          random.nextLong(),
          random.nextInt(),
                RandomStringGenerator.random(false, 2)
        );
    }
}
