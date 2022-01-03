package com.project.petboard.infrastructure.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseJwt {

    private String accessToken;
    private String refreshToken;
    private long accessTokenExpireTime;

    @Builder
    public ResponseJwt(String accessToken, String refreshToken, long assessTokenExpireTime){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpireTime = assessTokenExpireTime;
    }

}
