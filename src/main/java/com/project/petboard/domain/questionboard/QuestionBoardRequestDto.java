package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Getter
public class QuestionBoardRequestDto {

    private final Long questionBoardNumber;

    private final Long memberNumber;

    private final String questionBoardTitle;

    private final String questionBoardContents;

    public QuestionBoard toEntity(Member member){
        return  QuestionBoard.builder()
                .member(member)
                .questionBoardTitle(questionBoardTitle)
                .questionBoardContents(questionBoardContents)
                .build();
    }
}
