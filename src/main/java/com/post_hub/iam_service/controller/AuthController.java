package com.post_hub.iam_service.controller;

import com.post_hub.iam_service.model.constants.ApiLogMessage;
import com.post_hub.iam_service.model.dto.User.LoginRequest;
import com.post_hub.iam_service.model.dto.User.UserProfileDTO;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.service.AuthService;
import com.post_hub.iam_service.utils.ApiUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${end.point.auth}")
public class AuthController {
    private final AuthService authService;

    @PostMapping("${end.point.login}")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest request, HttpServletResponse response){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserProfileDTO> result = authService.login(request);
        Cookie authorizaitonCookie = ApiUtils.createAuthCookie(result.getPayload().getToken());
        response.addCookie(authorizaitonCookie);
        return ResponseEntity.ok(result);
    }

}
