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

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = false)
    @CreationTimestamp
    private Date memberJoinDate;

    @Enumerated(EnumType.STRING) //enum 설정
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    private MemberSingupCategory memberSingupCategory;

    @Builder
    public Member(String memberNickname,MemberSingupCategory memberSingupCategory,MemberStatus memberStatus){
        this.memberNickname = memberNickname;
        this.memberSingupCategory = memberSingupCategory;
        this.memberStatus = memberStatus;
    }

    /**
     * nickname 변경
     * @param memberNickname
     */
    public void NicknameChange(String memberNickname){
        this.memberNickname = memberNickname;
    }

}
