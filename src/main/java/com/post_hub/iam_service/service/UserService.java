package com.post_hub.iam_service.service;


import com.post_hub.iam_service.model.dto.User.UserDTO;
import com.post_hub.iam_service.model.request.User.NewUserRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface UserService {
    IamResponse<UserDTO> getById(@NotNull Long userId);
    IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest);
}
