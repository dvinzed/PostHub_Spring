package com.post_hub.iam_service.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String role;
    public static UserRole fromName(String name){
        return UserRole.valueOf(name.toUpperCase());
    }
}
