package com.project.petboard.domain.comment;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberResponseDto;
import com.project.petboard.domain.post.Post;
import lombok.Getter;

import java.util.Date;

@Getter
public class CommentResponseDto {

    private final Long commentNumber;

    private final MemberResponseDto memberResponseDto;

    private final String commentContents;

    private final Date commentCreateDate;

    public CommentResponseDto(Comment comment) {
        this.commentNumber = comment.getCommentNumber();
        this.memberResponseDto = new MemberResponseDto(comment.getMember());
        this.commentContents = comment.getCommentContents();
        this.commentCreateDate = comment.getCommentCreateDate();
    }
}
