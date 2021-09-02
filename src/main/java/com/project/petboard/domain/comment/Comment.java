package com.project.petboard.domain.comment;

import com.project.petboard.domain.board.Board;
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
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long CommentNumber;

    @ManyToOne
    @JoinColumn
    private Board board;

    @ManyToOne
    @JoinColumn
    private Member member;

    private String CommentContents;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = false)
    @CreationTimestamp
    private Date CommentCreateDate;

    @Temporal(value = TemporalType.DATE)
    @Column(insertable = true,updatable = true)
    @CreationTimestamp
    private Date CommentAmendDate;
}
