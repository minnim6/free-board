package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class QuestionBoard {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long questionBoardNumber;

    @ManyToOne
    @JoinColumn(name = "member_number")
    private Member member;

    private String questionBoardTitle;

    private String questionBoardContents;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date questionBoardCreateDate;

    @Temporal(TemporalType.DATE)
    @Column(insertable = false,updatable = false)
    @CreationTimestamp
    private Date questionBoardAnswerDate;

    private String questionBoardAnswer;

    @Builder
    public QuestionBoard(Member member,String questionBoardTitle,String questionBoardContents){
        this.member = member;
        this.questionBoardTitle = questionBoardTitle;
        this.questionBoardContents = questionBoardContents;
    }

    public void completeAnswer(QuestionBoardAnswerRequestDto answerRequestDto){
        this.questionBoardAnswer = answerRequestDto.getQuestionBoardAnswer();
        this.questionBoardAnswerDate = new Date();
    }

}
