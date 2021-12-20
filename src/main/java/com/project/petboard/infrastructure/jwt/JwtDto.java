package com.project.petboard.infrastructure.jwt;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class JwtDto {

    private final String refreshToken;
    private final long accessTokenExpireTime;

    @Builder
    public JwtDto(String refreshToken,long assessTokenExpireTime){
        this.refreshToken = refreshToken;
        this.accessTokenExpireTime = assessTokenExpireTime;
    }
}
