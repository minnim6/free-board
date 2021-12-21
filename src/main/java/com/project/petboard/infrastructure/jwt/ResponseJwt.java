package com.project.petboard.infrastructure.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseJwt {

    private final String accessToken;
    private final String refreshToken;
    private final long accessTokenExpireTime;

    @Builder
    public ResponseJwt(String accessToken, String refreshToken, long assessTokenExpireTime){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireTime = assessTokenExpireTime;
    }

}
