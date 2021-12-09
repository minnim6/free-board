package com.project.petboard.infrastructure.kakao;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberSingupCategory;
import lombok.Getter;

@Getter
public class KakaoAccount {

    private String email;
    private KakaoProfile profile;

}
