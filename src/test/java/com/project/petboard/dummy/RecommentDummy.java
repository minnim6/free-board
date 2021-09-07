package com.project.petboard.dummy;

import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.recomment.Recomment;
import lombok.Getter;

@Getter
public class RecommentDummy {

    private final Member member;
    private final Board board;
    private final Comment comment;
    private final String recommentContents = "대댓글";

    public RecommentDummy(Member member, Board board, Comment comment){
        this.member =member;
        this.board = board;
        this.comment = comment;
    }

    public Recomment toEntity(){
        return Recomment.builder()
                .member(member)
                .board(board)
                .comment(comment)
                .recommentContents(recommentContents)
                .build();
    }
}
