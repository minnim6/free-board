package com.project.petboard.domain.member;

import jdk.jfr.Enabled;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNumber;

    private String memberNickname;

    private String memberEmail;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = false)
    @CreationTimestamp
    private Date memberJoinDate;

    @Enumerated(EnumType.STRING) //enum 설정
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    private MemberSingupCategory memberSingupCategory;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Builder
    public Member(String memberNickname,MemberSingupCategory memberSingupCategory,String memberEmail){
        this.memberNickname = memberNickname;
        this.memberSingupCategory = memberSingupCategory;
        this.memberEmail = memberEmail;
        this.memberStatus = MemberStatus.Y;
        this.memberRole = MemberRole.MEMBER;
    }

    public void nicknameChange(String Nickname){
        this.memberNickname = Nickname;
    }

}
