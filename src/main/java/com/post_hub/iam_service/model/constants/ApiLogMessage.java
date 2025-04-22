package com.post_hub.iam_service.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;



public enum ApiLogMessage {
        POST_INFO_BY_ID("Receiving post with ID: %s"),
    ;

        private final String message;

        public String getMessage(Object... args) {
            return  String.format(message, args);
        }

    ApiLogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
