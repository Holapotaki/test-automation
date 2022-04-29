package com.test.automation.model;

import java.util.regex.Pattern;

public class EmailValidation {

    private static final String RFC5322_REGEX_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean validateUsingRFC5322Regex(String emailAddress) {
        return patternMatches(emailAddress, RFC5322_REGEX_PATTERN);
    }
}
