package com.coupon.utils;

public final class RandomGenerator {
    private static final Integer USER_NAME_LENGTH = 10;
    private static final Integer PASSWORD_LENGTH = 5;
    private static final Integer TOKEN_LENGTH = 15;

    private RandomGenerator() {

    }

    private static String getAlphaNumericString(final int n) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (alphaNumericString.length() * Math.random());

            sb.append(alphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public static String generateUserName() {
        return RandomGenerator.getAlphaNumericString(USER_NAME_LENGTH);
    }

    public static String generatePassword() {
        return RandomGenerator.getAlphaNumericString(PASSWORD_LENGTH);
    }

    public static String generateToken() {
        return RandomGenerator.getAlphaNumericString(TOKEN_LENGTH);
    }
}
