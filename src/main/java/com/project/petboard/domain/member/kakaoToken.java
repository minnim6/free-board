package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class kakaoToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
}
