package com.project.petboard.domain.comment;

import com.project.petboard.domain.member.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
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
