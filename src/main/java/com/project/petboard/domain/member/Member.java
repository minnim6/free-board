package com.project.petboard.domain.member;

import com.project.petboard.infrastructure.kakao.KakaoAccount;
import jdk.jfr.Enabled;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNumber;

    private String memberNickname;

    private String memberEmail;

    private String memberSnsId;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = false)
    @CreationTimestamp
    private Date memberJoinDate;

    @Enumerated(EnumType.STRING) //enum 설정
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    private MemberSignupCategory memberSignupCategory;

    @OneToMany(mappedBy = "member",fetch = FetchType.EAGER)
    private List<Role> memberRole = new ArrayList<>();

    @Builder
    public Member(String memberNickname,MemberSignupCategory memberSignupCategory,String memberEmail
    ,String memberSnsId){
        this.memberNickname = memberNickname;
        this.memberSignupCategory = memberSignupCategory;
        this.memberEmail = memberEmail;
        this.memberStatus = MemberStatus.Y;
        this.memberSnsId = memberSnsId;
    }

    public Member kakaoProfileUpdate(KakaoAccount kakaoAccount){
        this.memberNickname = kakaoAccount.getProfile().getNickname();
        this.memberEmail = kakaoAccount.getEmail();
        return this;
    }

}
