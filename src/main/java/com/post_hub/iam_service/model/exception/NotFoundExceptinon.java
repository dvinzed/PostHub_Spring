package com.post_hub.iam_service.model.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotFoundExceptinon extends RuntimeException {
    public NotFoundExceptinon(String message) {
        super(message);
    }
}
