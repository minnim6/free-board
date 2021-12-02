package com.project.petboard.dummy;

import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import lombok.Getter;

@Getter
public class CommentDummy {

    private final Post post;
    private final Member member;
    private final String commentContents = "댓글";


    public CommentDummy(Post post, Member member){
        this.post = post;
        this.member = member;
    }

    public Comment toEntity(){
        return Comment.builder()
                .post(post)
                .member(member)
                .commentContents(commentContents)
                .build();
    }
}
