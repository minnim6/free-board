package com.project.petboard.domain.member;

import com.project.petboard.infrastructure.kakao.KakaoAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "member")
    private List<Role> memberRole = new ArrayList<>();

    private String memberRefreshToken;

    @Temporal(value = TemporalType.TIMESTAMP ) //년월일 date 타입 db에 매핑
    private Date memberRefreshTokenExpireTime;

    @Builder
    public Member(String memberNickname,MemberSignupCategory memberSignupCategory,String memberEmail
    ,String memberSnsId){
        this.memberNickname = memberNickname;
        this.memberSignupCategory = memberSignupCategory;
        this.memberEmail = memberEmail;
        this.memberStatus = MemberStatus.Y;
        this.memberSnsId = memberSnsId;
    }

    public void setMemberRefreshToke(String refreshToke){
        this.memberRefreshToken = refreshToke;
    }

    public void setMemberRefreshTokenExpireTime(Date refreshTokenExpireTime){
        this.memberRefreshTokenExpireTime = refreshTokenExpireTime;
    }

    public void memberStatusChange(){
        this.memberNickname = "탈퇴한 회원입니다.";
        this.memberEmail = "null";
        this.memberSnsId = "null";
        this.memberStatus = MemberStatus.N;
        this.memberJoinDate = null;
        this.memberRefreshToken = null;
        this.memberRefreshTokenExpireTime = null;
    }

    public Member kakaoProfileUpdate(KakaoAccount kakaoAccount){
        this.memberNickname = kakaoAccount.getProfile().getNickname();
        this.memberEmail = kakaoAccount.getEmail();
        return this;
    }

}
