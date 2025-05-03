package com.post_hub.iam_service.service.impl;

import com.post_hub.iam_service.mapper.UserMapper;
import com.post_hub.iam_service.model.constants.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.User.UserDTO;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.exception.DataExistException;
import com.post_hub.iam_service.model.exception.NotFoundExceptinon;
import com.post_hub.iam_service.model.request.User.NewUserRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public IamResponse<UserDTO> getById(@NotNull Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        UserDTO userDTO = userMapper.toDto(user);
        return IamResponse.createSuccessful(userDTO);

    }

    @Override
    public IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest) {
    if(userRepository.existsByUsername(newUserRequest.getUsername())){
        throw new DataExistException(ApiErrorMessage.USERNAME_ALREADY_EXISTS.getMessage(newUserRequest.getUsername()));
    }

    if(userRepository.existsByEmail(newUserRequest.getEmail())){
        throw new DataExistException(ApiErrorMessage.EMAIL_ALREADY_EXISTS.getMessage(newUserRequest.getEmail()));
    }

    User user = userMapper.createUser(newUserRequest);
    User savedUser = userRepository.save(user);
    UserDTO userDTO = userMapper.toDto(savedUser);
    return IamResponse.createSuccessful(userDTO);

    }
}
