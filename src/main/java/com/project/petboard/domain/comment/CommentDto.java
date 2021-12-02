package com.project.petboard.domain.comment;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
public class CommentDto {

    private Long commentNumber;

    private Post post;

    private Member member;

    private String commentContents;

    private Date commentCreateDate;

    private Date commentAmendDate;

    public CommentDto(Comment comment) {
        this.commentNumber = comment.getCommentNumber();
        this.post = comment.getPost();
        this.member = comment.getMember();
        this.commentContents = comment.getCommentContents();
        this.commentCreateDate = comment.getCommentCreateDate();
        this.commentAmendDate = comment.getCommentAmendDate();
    }
    public Comment toEntity(){
        return Comment.builder()
                .commentContents(this.commentContents)
                .build();
    }
}
