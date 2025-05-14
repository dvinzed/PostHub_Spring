package com.post_hub.iam_service.service.impl;

import com.post_hub.iam_service.mapper.UserMapper;
import com.post_hub.iam_service.model.constants.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.User.LoginRequest;
import com.post_hub.iam_service.model.dto.User.UserProfileDTO;
import com.post_hub.iam_service.model.enteties.RefreshToken;
import com.post_hub.iam_service.model.enteties.Role;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.exception.InvalidDataException;
import com.post_hub.iam_service.model.exception.NotFoundExceptinon;
import com.post_hub.iam_service.model.request.User.RegistrationUserRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.repositories.RoleRepository;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.security.JwtTokenProvider;
import com.post_hub.iam_service.service.AuthService;
import com.post_hub.iam_service.service.RefreshTokenService;
import com.post_hub.iam_service.service.model.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public IamResponse<UserProfileDTO> login(@NotNull LoginRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch(BadCredentialsException e){
            throw new InvalidDataException(ApiErrorMessage.INVALID_USER_OR_PASSWORD.getMessage());
        }

        User user = userRepository.findByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new InvalidDataException(ApiErrorMessage.INVALID_USER_OR_PASSWORD.getMessage()));

        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(user);
        String token = jwtTokenProvider.generateToken(user);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDto(user,token, refreshToken.getToken());
        userProfileDTO.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileDTO);


    }

    @Override
    public IamResponse<UserProfileDTO> refreshAccessToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.validateAndRefreshToken(refreshTokenValue);
        User user = refreshToken.getUser();

        String accessToken = jwtTokenProvider.generateToken(user);

        return IamResponse.createSuccessfulWithNewToken(userMapper.toUserProfileDto(user,accessToken, refreshToken.getToken()));
    }

    @Override
    public IamResponse<UserProfileDTO> registerUser(@NotNull RegistrationUserRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new InvalidDataException(ApiErrorMessage.USERNAME_ALREADY_EXISTS.getMessage(request.getUsername()));
        });


        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new InvalidDataException(ApiErrorMessage.EMAIL_ALREADY_EXISTS.getMessage(request.getEmail()));
        });

        Role userRole = roleRepository.findByName(UserRole.USER.getRole())
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.USER_ROLE_NOT_FOUND.getMessage()));

        User newUser = userMapper.fromDTO(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);
        userRepository.save(newUser);
        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(newUser);
        String token = jwtTokenProvider.generateToken(newUser);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDto(newUser,token, refreshToken.getToken());
        return IamResponse.createSuccessfulWithNewToken(userProfileDTO);
    }

}
