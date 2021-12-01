package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Optional;

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

    public PostDto(Post post) {
        this.postNumber = post.getPostNumber();
        this.member = post.getMember();
        this.postCreateDate = post.getPostCreateDate();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
        this.postCategory = post.getPostCategory();
        this.postAmendDate = post.getPostAmendDate();
        this.postStatus = post.getPostStatus();
        this.postReportCount = post.getPostReportCount();
    }
    public Post toEntity(){
        return Post.builder()
                .member(member)
                .postTitle(postTitle)
                .postCategory(postCategory)
                .postContents(postContents)
                .build();
    }
}
