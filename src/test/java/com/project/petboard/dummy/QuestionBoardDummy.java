package com.project.petboard.dummy;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.questionboard.QuestionBoard;
import lombok.Getter;

@Getter
public class QuestionBoardDummy {

    private final Member member;
    private final String questionBoardTitle = "질문";
    private final String questionBoardContents = "질문내용";

    public QuestionBoardDummy(Member member){
        this.member = member;
    }

    public QuestionBoard toEntity(){
        return QuestionBoard.builder()
                .member(member)
                .questionBoardTitle(questionBoardTitle)
                .questionBoardContents(questionBoardContents)
                .build();
    }
}
