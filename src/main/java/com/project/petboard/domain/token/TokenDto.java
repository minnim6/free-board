package com.project.petboard.domain.token;

import java.util.Date;

public class TokenDto {

    private Long tokenNumber;

    private String refreshToken;

    private Date refreshTokenExpireTime;

    public Token toEntity(){
       return Token.builder()
               .refreshTokenExpireTime(refreshTokenExpireTime)
               .refreshToken(refreshToken)
               .build();
    }
}
