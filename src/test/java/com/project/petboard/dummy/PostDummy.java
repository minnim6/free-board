package com.project.petboard.dummy;

import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.member.Member;
import lombok.Getter;

@Getter
public class PostDummy {

    private final Member member;
    private final String postCategory = "카테고리";
    private final String postTitle = "제목";
    private final String postContents = "내용";


    public PostDummy(Member member){
        this.member = member;
    }

    public Post toEntity(){
        return Post.builder()
                .member(member)
                .postCategory(postCategory)
                .postTitle(postTitle)
                .postContents(postContents)
                .build();
    }
}
