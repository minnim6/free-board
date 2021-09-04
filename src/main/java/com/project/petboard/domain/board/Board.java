package com.project.petboard.domain.board;

import com.project.petboard.domain.member.Member;
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

    @ManyToOne
    @JoinColumn
    private Member member;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = false)
    @CreationTimestamp
    private Date BoardCreateDate;

    private String BoardTitle;

    private String BoardContents;
    
    private String BoardCategory;

    @Temporal(value = TemporalType.DATE) //년월일 date 타입 db에 매핑
    @Column(insertable = true, updatable = true)
    @CreationTimestamp
    private Date BoardAmendDate;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    @Builder
    public Board(Member member,String boardTitle,String boardCategory,String boardContents){
        this.member = member;
        this.BoardCategory = boardCategory;
        this.BoardTitle =  boardTitle;
        this.BoardContents = boardContents;
        this.boardStatus = BoardStatus.Y;
    }
    // 1. UpdateContents 2. UpdateBoardContents
    public void Update(String boardTitle,String boardContents,String boardCategory){
        this.BoardTitle = boardTitle;
        this.BoardContents = boardContents;
        this.BoardCategory = boardCategory;
    }

    public void blind(){
        this.boardStatus = BoardStatus.N;
    }
}
