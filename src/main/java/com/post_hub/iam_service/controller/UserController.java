package com.post_hub.iam_service.controller;


import com.post_hub.iam_service.model.constants.ApiLogMessage;
import com.post_hub.iam_service.model.dto.User.UserDTO;
import com.post_hub.iam_service.model.dto.User.UserSearchDTO;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.request.User.NewUserRequest;
import com.post_hub.iam_service.model.request.User.UpdateUserRequest;
import com.post_hub.iam_service.model.request.User.UserSearchRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.service.UserService;
import com.post_hub.iam_service.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("${end.point.users}")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("${end.point.id}")
    public ResponseEntity<IamResponse<UserDTO>> getUserById(
            @PathVariable(name = "id") Long userId){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.getById(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("${end.point.create}")
    public ResponseEntity<IamResponse<UserDTO>> createUser(@RequestBody @Valid NewUserRequest newUserRequest){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDTO> response = userService.createUser(newUserRequest);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("${end.point.id}")
    public ResponseEntity<Void> softDeleteUserById(
            @PathVariable(name = "id") Long userId){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        userService.softDeleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("${end.point.id}")
    public ResponseEntity<IamResponse<UserDTO>> updateUserById(
            @PathVariable(name = "id") Long userId,
            @RequestBody @Valid UpdateUserRequest updateUserRequest){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        IamResponse<UserDTO> updatedUser = userService.updateUser(userId,updateUserRequest);
        return ResponseEntity.ok(updatedUser);

    }


    @GetMapping("${end.point.all}")
    public ResponseEntity<IamResponse<PaginationResponse<UserSearchDTO>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<UserSearchDTO>> response = userService.findAllUsers(pageable);
        return ResponseEntity.ok(response);
    }


    @PostMapping("${end.point.search}")
    public ResponseEntity<IamResponse<PaginationResponse<UserSearchDTO>>> searchUsers(
            @RequestBody @Valid UserSearchRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {

        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<UserSearchDTO>> response = userService.searchUsers(request, pageable);
        return ResponseEntity.ok(response);
    }





}
