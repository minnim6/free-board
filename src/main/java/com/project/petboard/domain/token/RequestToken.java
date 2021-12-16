package com.project.petboard.domain.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class RequestToken {
    private final String accessToken;
    private final String refreshToken;
    private final Date refreshTokenExpireTime;

    public RequestToken(Token token){
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
        this.refreshTokenExpireTime = token.getRefreshTokenExpireTime();
    }
}
