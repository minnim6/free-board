package com.project.petboard.infrastructure.kakao;

import com.project.petboard.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KakaoAccount {
    private String email;
    private KakaoProfile profile;

}
