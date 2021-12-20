package com.project.petboard.domain.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class ResponseTokenDto {
    private String accessToken;
    private Date accessTokenExpireTime;

    public ResponseTokenDto(String accessToken, Date accessTokenExpireTime) {
        this.accessToken = accessToken;
        this.accessTokenExpireTime = accessTokenExpireTime;
    }
}
