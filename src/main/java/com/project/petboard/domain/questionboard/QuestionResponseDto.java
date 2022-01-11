package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class QuestionResponseDto {

    private final Long questionBoardNumber;

    private final MemberResponseDto memberResponseDto;

    private final String questionBoardTitle;

    private final String questionBoardContents;

    private final Date questionBoardCreateDate;

    private final Date questionBoardAnswerDate;

    private final String questionBoardAnswer;

    public QuestionResponseDto(QuestionBoard questionBoard){
        this.questionBoardNumber = questionBoard.getQuestionBoardNumber();
        this.memberResponseDto = new MemberResponseDto(questionBoard.getMember());
        this.questionBoardTitle = questionBoard.getQuestionBoardTitle();
        this.questionBoardContents = questionBoard.getQuestionBoardContents();
        this.questionBoardCreateDate = questionBoard.getQuestionBoardCreateDate();
        this.questionBoardAnswer = questionBoard.getQuestionBoardAnswer();
        this.questionBoardAnswerDate = questionBoard.getQuestionBoardAnswerDate();
    }

}
