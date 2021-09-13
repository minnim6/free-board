package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;

import java.util.Date;

public class PostDto {

    private Long postNumber;

    private Member member;

    private Date postCreateDate;

    private String postTitle;

    private String postContents;

    private String postCategory;

    private Date postAmendDate;

    private PostStatus postStatus;

    public Post toEntity(){
        return Post.builder()
                .member(member)
                .postTitle(postTitle)
                .postCategory(postCategory)
                .postContents(postContents)
                .build();
    }
}
