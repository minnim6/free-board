package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {

    private String memberNickname;

    public MemberRequestDto(Member member){
        this.memberNickname = member.getMemberNickname();
    }

}
