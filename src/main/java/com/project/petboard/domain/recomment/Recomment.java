package com.project.petboard.domain.recomment;

import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import lombok.Builder;
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
    private Long recommentNumber;

    @ManyToOne
    @JoinColumn
    private Board board;

    @ManyToOne
    @JoinColumn
    private Member member;

    @ManyToOne
    @JoinColumn
    private Comment comment;

    private String recommentContents;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date recommentCreateDate;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = true)
    @CreationTimestamp
    private Date recommentAmendDate;

    @Builder
    public Recomment(Member member,Board board,Comment comment,String recommentContents){
        this.member = member;
        this.board = board;
        this.comment = comment;
        this.recommentContents = recommentContents;
    }

    public void update(String recommentContents){
        this.recommentContents = recommentContents;
    }
}
