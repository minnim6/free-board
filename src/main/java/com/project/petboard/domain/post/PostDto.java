package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import lombok.Getter;

import java.util.Date;

@Getter
public class PostDto {

    private Long postNumber;

    private Member member;

    private Date postCreateDate;

    private String postTitle;

    private String postContents;

    private String postCategory;

    private Date postAmendDate;

    private PostStatus postStatus;

    private int postReportCount;

    public Post toEntity(){
        return Post.builder()
                .member(member)
                .postTitle(postTitle)
                .postCategory(postCategory)
                .postContents(postContents)
                .build();
    }
}
