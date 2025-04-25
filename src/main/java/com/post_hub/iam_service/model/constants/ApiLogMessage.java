package com.post_hub.iam_service.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter

public enum ApiLogMessage {
        POST_INFO_BY_ID("Receiving post with ID: {}"),
        NAME_OF_CURRENT_METHOD("Current method: {}"),
    ;

        private final String value;


    public String getValue() {
        return value;
    }

    ApiLogMessage(String value) {
        this.value = value;
    }
}
