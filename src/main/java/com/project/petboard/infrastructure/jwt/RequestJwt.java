package com.project.petboard.infrastructure.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestJwt {

    private final String accessToken;
    private final String refreshToken;
    private final long accessTokenExpireTime;

    @Builder
    public RequestJwt(String accessToken, String refreshToken, long assessTokenExpireTime){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireTime = assessTokenExpireTime;
    }

}
