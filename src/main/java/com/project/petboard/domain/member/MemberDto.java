package com.project.petboard.domain.member;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
public class MemberDto {

    private Long memberNumber;

    private String memberNickname;

    private Date memberJoinDate;

    private MemberStatus memberStatus;

    private MemberSingupCategory memberSingupCategory;
}
