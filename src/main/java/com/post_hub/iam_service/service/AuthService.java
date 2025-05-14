package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.User.LoginRequest;
import com.post_hub.iam_service.model.dto.User.UserProfileDTO;
import com.post_hub.iam_service.model.request.User.RegistrationUserRequest;
import com.post_hub.iam_service.model.response.IamResponse;

public interface AuthService {

    IamResponse<UserProfileDTO> login(LoginRequest request);
    IamResponse<UserProfileDTO> refreshAccessToken(String refreshToken);
    IamResponse<UserProfileDTO> registerUser(RegistrationUserRequest request);

}
