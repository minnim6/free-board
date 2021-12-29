package com.project.petboard.domain.post;

import com.project.petboard.domain.member.MemberRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private  Long postNumber;

    private  MemberRequestDto memberRequestDto;

    private  String postTitle;

    private  String postContents;

    private  String postCategory;

    public PostResponseDto(Post post){
        this.memberRequestDto = post.toMemberRequestDto();
        this.postNumber = post.getPostNumber();;
        this.postCategory = post.getPostCategory();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
    }
}
