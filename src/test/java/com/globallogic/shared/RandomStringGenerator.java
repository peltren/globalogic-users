package com.globallogic.shared;

import java.util.Random;

public class RandomStringGenerator {

    public static String random(boolean isAlphaNumeric, int maxLength){
        Random random = new Random();

        int leftLimit = isAlphaNumeric ? 48 : 97; // numeral '0' or letter 'a'
        int rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(maxLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
