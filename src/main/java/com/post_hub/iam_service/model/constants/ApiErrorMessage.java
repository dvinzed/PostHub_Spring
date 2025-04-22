package com.post_hub.iam_service.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


public enum ApiErrorMessage {
    POST_NOT_FOUND_BY_ID("POST WITH ID: %s was not found"),
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
