package com.post_hub.iam_service.service.impl;

import com.post_hub.iam_service.config.SecurityConfig;
import com.post_hub.iam_service.mapper.UserMapper;
import com.post_hub.iam_service.model.constants.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.User.UserDTO;
import com.post_hub.iam_service.model.dto.User.UserSearchDTO;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.exception.DataExistException;
import com.post_hub.iam_service.model.exception.NotFoundExceptinon;
import com.post_hub.iam_service.model.request.User.NewUserRequest;
import com.post_hub.iam_service.model.request.User.UpdateUserRequest;
import com.post_hub.iam_service.model.request.User.UserSearchRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.repositories.criteria.UserSearchCriteria;
import com.post_hub.iam_service.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
    user.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));
    User savedUser = userRepository.save(user);
    UserDTO userDTO = userMapper.toDto(savedUser);
    return IamResponse.createSuccessful(userDTO);

    }

    @Override
    public IamResponse<UserDTO> updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));
        userMapper.updateUser(user,updateUserRequest);
        user = userRepository.save(user);

        UserDTO userDTO = userMapper.toDto(user);

        return IamResponse.createSuccessful(userDTO);

    }

    @Override
    public void softDeleteUser(Long userId) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public IamResponse<PaginationResponse<UserSearchDTO>> findAllUsers(Pageable pageable) {
        Page<UserSearchDTO> users = userRepository.findAll(pageable)
                .map(userMapper::toUserSearchDto);

        PaginationResponse<UserSearchDTO> paginationResponse = new PaginationResponse<>(
                users.getContent(),
                new PaginationResponse.Pagination(
                        users.getTotalElements(),
                        pageable.getPageSize(),
                        users.getNumber() + 1,
                        users.getTotalPages()
                )
        );

        return IamResponse.createSuccessful(paginationResponse);
    }


    @Override
    public IamResponse<PaginationResponse<UserSearchDTO>> searchUsers(UserSearchRequest request, Pageable pageable) {
        Specification<User> specification = new UserSearchCriteria(request);
        Page<UserSearchDTO> users = userRepository.findAll(specification, pageable)
                .map(userMapper::toUserSearchDto);

        PaginationResponse<UserSearchDTO> paginationResponse = PaginationResponse.<UserSearchDTO>builder()
                .content(users.getContent())
                .pagination(PaginationResponse.Pagination.builder()
                        .total(users.getTotalElements())
                        .limit(pageable.getPageSize())
                        .page(users.getNumber() + 1)
                        .pages(users.getTotalPages())
                        .build())
                .build();

        return IamResponse.createSuccessful(paginationResponse);
    }

}
