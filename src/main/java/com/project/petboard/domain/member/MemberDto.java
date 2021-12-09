package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;

@RequiredArgsConstructor
@Getter
public class MemberDto {

    private final Long memberNumber;

    private final String memberNickname;

    private final Date memberJoinDate;

    private final MemberStatus memberStatus;

    private final MemberSingupCategory memberSingupCategory;

    private final MemberRole memberRole;

    private final String memberEmail;

    public Member toEntity(){
        return Member.builder()
                .memberNickname(memberNickname)
                .memberEmail(memberEmail)
                .memberSingupCategory(memberSingupCategory)
                .build();
    }

    public MemberDto(Member member){
        this.memberNumber = member.getMemberNumber();
        this.memberNickname = member.getMemberNickname();
        this.memberJoinDate = member.getMemberJoinDate();
        this.memberStatus = member.getMemberStatus();
        this.memberSingupCategory = member.getMemberSingupCategory();
        this.memberRole = member.getMemberRole();
        this.memberEmail = member.getMemberEmail();
    }


}
