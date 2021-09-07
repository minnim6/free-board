package com.project.petboard.dummy;

import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDummy {

    private final Member member;
    private final String boardCategory = "카테고리";
    private final String boardTitle = "제목";
    private final String boardContents = "내용";


    public BoardDummy(Member member){
        this.member = member;
    }

    public Board toEntity(){
        return Board.builder()
                .member(member)
                .boardCategory(boardTitle)
                .boardTitle(boardTitle)
                .boardContents(boardTitle)
                .build();
    }
}
