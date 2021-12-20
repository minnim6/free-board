package com.project.petboard.domain.token;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
public class RequestToken {
    private final String accessToken;
    private final String refreshToken;
    private final Date refreshTokenExpireTime;

    @Builder
    public RequestToken(String accessToken,String refreshToken,Date refreshTokenExpireTime){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public Token toEntity(){
        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }
}
