package com.post_hub.iam_service.utils;

import com.post_hub.iam_service.service.model.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserRoleTypeConverter implements AttributeConverter<UserRole,String> {

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        return userRole.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String s) {
        return UserRole.fromName(s);
    }
}
