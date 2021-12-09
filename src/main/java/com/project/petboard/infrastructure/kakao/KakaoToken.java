package com.project.petboard.infrastructure.kakao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
public class KakaoToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
}
