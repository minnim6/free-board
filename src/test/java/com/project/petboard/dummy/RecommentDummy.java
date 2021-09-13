package com.project.petboard.dummy;

import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.recomment.Recomment;
import lombok.Getter;

@Getter
public class RecommentDummy {

    private final Member member;
    private final Post post;
    private final Comment comment;
    private final String recommentContents = "대댓글";

    public RecommentDummy(Member member, Post post, Comment comment){
        this.member =member;
        this.post = post;
        this.comment = comment;
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
