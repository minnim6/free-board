package com.project.petboard.domain.member.kakao;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberSingupCategory;
import lombok.Getter;
import lombok.ToString;

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
