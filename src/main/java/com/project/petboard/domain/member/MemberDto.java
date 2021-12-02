package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    public MemberDto(Member member){
        this.memberNumber = member.getMemberNumber();
        this.memberNickname = member.getMemberNickname();
        this.memberJoinDate = member.getMemberJoinDate();
        this.memberStatus = member.getMemberStatus();
        this.memberSingupCategory = member.getMemberSingupCategory();
    }
}
