package com.project.petboard.infrastructure.jwt;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class JwtDto {
    private final String accessToken;
    private final String refreshToken;
    private final long accessTokenExpireTime;

    @Builder
    public JwtDto(String accessToken,String refreshToken,long assessTokenExpireTime){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireTime = assessTokenExpireTime;
    }
}
