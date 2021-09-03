package com.project.petboard.domain.recomment;

import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.lang.ref.PhantomReference;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Recomment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long RecommentNumber;

    @ManyToOne
    @JoinColumn
    private Board board;

    @ManyToOne
    @JoinColumn
    private Member member;

    @ManyToOne
    @JoinColumn
    private Comment comment;

    private String RecommentContents;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date RecommentCreateDate;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = true)
    @CreationTimestamp
    private Date RecommentAmendDate;
}
