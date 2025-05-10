package com.post_hub.iam_service.service;


import com.post_hub.iam_service.model.dto.User.UserDTO;
import com.post_hub.iam_service.model.dto.User.UserSearchDTO;
import com.post_hub.iam_service.model.request.User.NewUserRequest;
import com.post_hub.iam_service.model.request.User.UpdateUserRequest;
import com.post_hub.iam_service.model.request.User.UserSearchRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    IamResponse<UserDTO> getById(@NotNull Long userId);
    IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest);
    IamResponse<UserDTO> updateUser(@NotNull Long userId, @NotNull UpdateUserRequest updateUserRequest);
    void softDeleteUser(@NotNull Long userId);
    IamResponse<PaginationResponse<UserSearchDTO>> findAllUsers(Pageable pageable);
    IamResponse<PaginationResponse<UserSearchDTO>> searchUsers(UserSearchRequest request, Pageable pageable);
}
