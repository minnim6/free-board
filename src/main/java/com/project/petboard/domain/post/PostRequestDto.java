package com.project.petboard.domain.post;

import com.project.petboard.domain.member.MemberRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private  Long postNumber;

    private  MemberRequestDto memberRequestDto;

    private  String postTitle;

    private  String postContents;

    private  String postCategory;

    public PostRequestDto(Post post){
        this.memberRequestDto = post.toMemberRequestDto();
        this.postNumber = post.getPostNumber();;
        this.postCategory = post.getPostCategory();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
    }
}
