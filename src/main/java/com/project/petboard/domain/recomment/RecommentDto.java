package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
public class RecommentDto {

    private Long recommentNumber;

    private Post post;

    private Member member;

    private Comment comment;

    private String recommentContents;

    private Date recommentCreateDate;

    private Date recommentAmendDate;

    public RecommentDto(Recomment recomment){
        this.recommentNumber = recomment.getRecommentNumber();
        this.post = recomment.getPost();
        this.member = recomment.getMember();
        this.comment = recomment.getComment();
        this.recommentContents = recomment.getRecommentContents();
        this.recommentCreateDate = recomment.getRecommentCreateDate();
        this.recommentAmendDate = recomment.getRecommentAmendDate();
    }
    public Recomment toEntity(){
        return Recomment.builder()
                .member(member)
                .post(post)
                .comment(comment)
                .recommentContents(recommentContents)
                .build();
    }
}
