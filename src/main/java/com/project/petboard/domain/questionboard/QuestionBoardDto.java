package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
public class QuestionBoardDto{

    private final Long questionBoardNumber;

    private final Member member;

    private final String questionBoardTitle;

    private final String questionBoardContents;

    private final Date questionBoardCreateDate;

    private final Date questionBoardAnswerDate;

    private final String questionBoardAnswer;

    public QuestionBoard toEntity(){
        return  QuestionBoard.builder()
                .member(member)
                .questionBoardTitle(questionBoardTitle)
                .questionBoardContents(questionBoardContents)
                .build();
    }

    public QuestionBoardDto(QuestionBoard questionBoard){
        this.questionBoardNumber = questionBoard.getQuestionBoardNumber();
        this.member = questionBoard.getMember();
        this.questionBoardTitle = questionBoard.getQuestionBoardTitle();
        this.questionBoardContents = questionBoard.getQuestionBoardContents();
        this.questionBoardCreateDate = questionBoard.getQuestionBoardCreateDate();
        this.questionBoardAnswerDate = questionBoard.getQuestionBoardAnswerDate();
        this.questionBoardAnswer = questionBoard.getQuestionBoardAnswer();
    }
}
