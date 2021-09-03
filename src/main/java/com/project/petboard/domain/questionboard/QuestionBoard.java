package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import lombok.Data;
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
    private Long QuestionBoardNumber;

    @ManyToOne
    @JoinColumn
    private Member member;

    private String QuestionBoardTitle;

    private String QuestionBoardContents;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date QuestionBoardCreateDate;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date QuestionBoardCommentDate;

    private String QuestionBoardComment;

}
