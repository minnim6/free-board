package com.project.petboard.domain.recomment;

import com.project.petboard.domain.member.MemberResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class RecommentResponseDto {

    private final Long recommentNumber;

    private final MemberResponseDto memberResponseDto;

    private final String recommentContents;

    private final Date recommentCreateDate;

    public RecommentResponseDto(Recomment recomment){
        this.recommentNumber = recomment.getRecommentNumber();
        this.memberResponseDto = new MemberResponseDto(recomment.getMember());
        this.recommentContents = recomment.getRecommentContents();
        this.recommentCreateDate = recomment.getRecommentCreateDate();
    }

}
