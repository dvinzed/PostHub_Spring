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
