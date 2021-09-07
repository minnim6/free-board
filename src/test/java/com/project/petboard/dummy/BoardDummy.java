package com.project.petboard.dummy;

import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.member.Member;
import lombok.Getter;

@Getter
public class BoardDummy {

    private final Member member;
    private final String BoardCategory = "카테고리";
    private final String BoardTitle = "제목";
    private final String BoardContents = "내용";

    public BoardDummy(Member member){
        this.member = member;
    }

    public Board toEntity(){
        return Board.builder()
                .member(member)
                .boardCategory(BoardTitle)
                .boardTitle(BoardTitle)
                .boardContents(BoardTitle)
                .build();
    }
}
