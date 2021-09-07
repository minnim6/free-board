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
    private Long questionBoardNumber;

    @ManyToOne
    @JoinColumn
    private Member member;

    private String questionBoardTitle;

    private String questionBoardContents;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date questionBoardCreateDate;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date questionBoardCommentDate;

    private String questionBoardComment;

}
