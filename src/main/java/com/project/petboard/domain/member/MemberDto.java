package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class MemberDto {

    private final Long memberNumber;

    private final String memberNickname;

    private String memberSnsId;

    private final Date memberJoinDate;

    private final MemberStatus memberStatus;

    private final MemberSignupCategory memberSignupCategory;

    private final List<Role> memberRole;

    private final String memberEmail;

    public Member toEntity(){
        return Member.builder()
                .memberNickname(memberNickname)
                .memberEmail(memberEmail)
                .memberSignupCategory(memberSignupCategory)
                .memberSnsId(memberSnsId)
                .build();
    }

    public MemberDto(Member member){
        this.memberNumber = member.getMemberNumber();
        this.memberNickname = member.getMemberNickname();
        this.memberJoinDate = member.getMemberJoinDate();
        this.memberStatus = member.getMemberStatus();
        this.memberSignupCategory = member.getMemberSignupCategory();
        this.memberRole = member.getMemberRole();
        this.memberEmail = member.getMemberEmail();
        this.memberSnsId = member.getMemberSnsId();
    }


}
