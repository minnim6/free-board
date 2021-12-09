package com.project.petboard.infrastructure.kakao;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberSingupCategory;
import lombok.Getter;

@Getter
public class RequestKakao {
    private Long id;
    private KakaoAccount kakao_account;

    public Member toEntity(){
        return Member.builder()
                .memberNickname(kakao_account.getProfile().getNickname())
                .memberEmail(kakao_account.getEmail())
                .memberSingupCategory(MemberSingupCategory.KAKAO)
                .memberSnsId(String.valueOf(id))
                .build();
    }
}
