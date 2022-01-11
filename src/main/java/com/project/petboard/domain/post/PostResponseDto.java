package com.project.petboard.domain.post;

import com.project.petboard.domain.member.MemberResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {

    private final Long postNumber;

    private final MemberResponseDto memberResponseDto;

    private final String postTitle;

    private final String postContents;

    private final String postCategory;

    public PostResponseDto(Post post){
        this.memberResponseDto = post.toMemberResponseDto();
        this.postNumber = post.getPostNumber();;
        this.postCategory = post.getPostCategory();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
    }

}
