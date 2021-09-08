package com.project.petboard.domain.board;

import com.project.petboard.domain.member.Member;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Board {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long BoardNumber;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Member member;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = false)
    @CreationTimestamp
    private Date BoardCreateDate;

    private String boardTitle;

    private String boardContents;
    
    private String boardCategory;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = true)
    @CreationTimestamp
    private Date boardAmendDate;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    @Builder
    public Board(Member member,String boardTitle,String boardCategory,String boardContents){
        this.member = member;
        this.boardCategory = boardCategory;
        this.boardTitle =  boardTitle;
        this.boardContents = boardContents;
        this.boardStatus = BoardStatus.Y;
    }
    // 1. UpdateContents 2. UpdateBoardContents
    public void update(String boardTitle,String boardContents,String boardCategory){
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardCategory = boardCategory;
    }

    public void blind(){
        this.boardStatus = BoardStatus.N;
    }
}
