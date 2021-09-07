package com.project.petboard.dummy;

import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDummy {

    private final Board board;
    private final Member member;
    private final String commentContents = "댓글";


    public CommentDummy(Board board,Member member){
        this.board = board;
        this.member = member;
    }

    public Comment toEntity(){
        return Comment.builder()
                .board(board)
                .member(member)
                .commentContents(commentContents)
                .build();
    }
}
