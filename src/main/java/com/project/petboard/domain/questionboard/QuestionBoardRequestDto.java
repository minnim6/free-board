package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
public class QuestionBoardRequestDto {

    private Long questionBoardNumber;

    private Long memberNumber;

    private String questionBoardTitle;

    private String questionBoardContents;

    public QuestionBoard toEntity(Member member){
        return  QuestionBoard.builder()
                .member(member)
                .questionBoardTitle(questionBoardTitle)
                .questionBoardContents(questionBoardContents)
                .build();
    }
}
