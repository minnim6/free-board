package com.project.petboard.dummy;

import com.project.petboard.domain.member.MemberSignupCategory;
import com.project.petboard.domain.member.MemberStatus;
import lombok.Getter;


@Getter
public class MemberDummy {

    private final String memberNickname = "회원1";
    private final MemberSignupCategory memberSingupCategory = MemberSignupCategory.KAKAO;
    private final MemberStatus memberStatus = MemberStatus.Y;

}