package com.project.petboard.infrastructure.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class ResponseJwt {

    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpireTime;

    @Builder
    public ResponseJwt(String accessToken, String refreshToken, Date assessTokenExpireTime){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireTime = assessTokenExpireTime;
    }

}
