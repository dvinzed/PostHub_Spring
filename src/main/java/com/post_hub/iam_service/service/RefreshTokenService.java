package com.post_hub.iam_service.service;


import com.post_hub.iam_service.model.enteties.RefreshToken;
import com.post_hub.iam_service.model.enteties.User;

public interface RefreshTokenService {
    RefreshToken generateOrUpdateRefreshToken(User user);
    RefreshToken validateAndRefreshToken(String refreshToken);

}
