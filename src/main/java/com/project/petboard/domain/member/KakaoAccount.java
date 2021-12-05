package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class KakaoAccount {

    private String email;
    private KakaoProfile profile;

    public Member toEntity(){
        return Member.builder()
                .memberNickname(profile.getNickname())
                .memberEmail(email)
                .memberSingupCategory(MemberSingupCategory.KAKAO)
                .build();
    }
}
