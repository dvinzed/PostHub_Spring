package com.post_hub.iam_service.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


public enum ApiErrorMessage {
    POST_NOT_FOUND_BY_ID("POST WITH ID: %s was not found"),
    POST_ALREADY_EXISTS("POST WITH TITLE: %s already exists"),
    USER_NOT_FOUND_BY_ID("USER WITH ID: %s was not found"),
    USERNAME_ALREADY_EXISTS("USERNAME WITH NICKNAME: %s already exists"),
    EMAIL_ALREADY_EXISTS("EMAIL WITH EMAIL: %s already exists"),
    EMAIL_NOT_EXIST("EMAIL WITH EMAIL: %s NOT EXIST"),
    USER_ROLE_NOT_FOUND("USER ROLE WITH ID: %s was not found"),

    ERROR_DURING_JWT_PROCCESING("An unexpected error occurred during JWT processing"),
    TOKEN_EXPIRED("Token expired"),
    UNEXPECTED_ERROR("An unexpected error occurred,please try again later"),
    INVALID_TOKEN("Invalid token"),

    AUTHENTICATION_FAILED_FOR_USER("Authentication failed for user: {}. "),
    INVALID_USER_OR_PASSWORD("Invalid email or password. Try again"),
    INVALID_USER_REGISTRATION_STATUS("Invalid user registration status: %s. "),
    NOT_FOUND_REFRESH_TOKEN("Refresh token not found."),
    MISMATCH_PASSWORDS("Password does not match"),
    INVALID_PASSWORD("Invalid password. It must have: "
            + "length at least " + ApiConstants.REQUIRED_MIN_PASSWORD_LENGTH + ", including "
            + ApiConstants.REQUIRED_MIN_LETTERS_NUMBER_EVERY_CASE_IN_PASSWORD + " letter(s) in upper and lower cases, "
            + ApiConstants.REQUIRED_MIN_CHARACTERS_NUMBER_IN_PASSWORD + " character(s), "
            + ApiConstants.REQUIRED_MIN_DIGITS_NUMBER_IN_PASSWORD + " digit(s). "),


    ;



    private final String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

    ApiErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
