package com.post_hub.iam_service.service.impl;

import com.post_hub.iam_service.model.constants.ApiErrorMessage;
import com.post_hub.iam_service.model.enteties.RefreshToken;
import com.post_hub.iam_service.model.enteties.User;
import com.post_hub.iam_service.model.exception.NotFoundExceptinon;
import com.post_hub.iam_service.repositories.RefreshTokenRepository;
import com.post_hub.iam_service.service.RefreshTokenService;
import com.post_hub.iam_service.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateOrUpdateRefreshToken(User user) {
        return refreshTokenRepository.findByUserId(user.getId())
                .map(refreshToken -> {
                    refreshToken.setCreated(LocalDateTime.now());
                    refreshToken.setToken(ApiUtils.generateUuiWithoudDash());
                    return refreshTokenRepository.save(refreshToken);
                })
                .orElseGet(() ->{
                    RefreshToken newToken = new RefreshToken();
                    newToken.setUser(user);
                    newToken.setCreated(LocalDateTime.now());
                    newToken.setToken(ApiUtils.generateUuiWithoudDash());
                   return refreshTokenRepository.save(newToken);

                });
    }

    @Override
    public RefreshToken validateAndRefreshToken(String requestRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new NotFoundExceptinon(ApiErrorMessage.NOT_FOUND_REFRESH_TOKEN.getMessage()));

        refreshToken.setCreated(LocalDateTime.now());
        refreshToken.setToken(ApiUtils.generateUuiWithoudDash());
        return refreshTokenRepository.save(refreshToken);
    }   
}
