package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@Getter
public class KakaoToken {
    private String access_token;
    private String token_type;
    private String refresh_token;

}
