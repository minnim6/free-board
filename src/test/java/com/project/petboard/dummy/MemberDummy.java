package com.project.petboard.dummy;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.MemberSingupCategory;
import com.project.petboard.domain.member.MemberStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
public class MemberDummy {

    private final String MemberNickname = "회원1";
    private final MemberSingupCategory memberSingupCategory = MemberSingupCategory.KAKAO;
    private final MemberStatus memberStatus = MemberStatus.Y;

    public Member toEntity() {
        return Member.builder()
                .memberNickname(MemberNickname)
                .memberSingupCategory(memberSingupCategory)
                .memberStatus(memberStatus)
                .build();
    }
}