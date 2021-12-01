package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
public class QuestionBoardDto{

    private Long questionBoardNumber;

    private Member member;

    private String questionBoardTitle;

    private String questionBoardContents;

    private Date questionBoardCreateDate;

    private Date questionBoardAnswerDate;

    private String questionBoardAnswer;

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
