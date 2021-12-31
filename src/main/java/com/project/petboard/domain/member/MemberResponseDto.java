package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long memberNumber;
    private String memberNickname;

    public MemberResponseDto(Member member){
        this.memberNumber = member.getMemberNumber();
        this.memberNickname = member.getMemberNickname();
    }

}
