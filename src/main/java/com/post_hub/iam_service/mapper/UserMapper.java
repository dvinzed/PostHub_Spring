package com.post_hub.iam_service.mapper;

import com.post_hub.iam_service.model.dto.User.UserDTO;
import com.post_hub.iam_service.model.dto.User.UserSearchDTO;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.enums.RegistrationStatus;
import com.post_hub.iam_service.model.request.User.NewUserRequest;
import com.post_hub.iam_service.model.request.User.UpdateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {RegistrationStatus.class, Object.class}
)
public interface UserMapper {

    @Mapping(source = "last_login", target = "lastLogin")
    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "registrationStatus", expression = "java(RegistrationStatus.ACTIVE)")
    User createUser(NewUserRequest newUserRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "registrationStatus", expression = "java(RegistrationStatus.ACTIVE)")
    void updateUser(@MappingTarget User user , UpdateUserRequest updateUserRequest);

    UserSearchDTO toUserSearchDto(User user);
}
