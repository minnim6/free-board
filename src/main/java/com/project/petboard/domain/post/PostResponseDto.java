package com.project.petboard.domain.post;

import com.project.petboard.domain.member.MemberResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private  Long postNumber;

    private MemberResponseDto memberResponseDto;

    private  String postTitle;

    private  String postContents;

    private  String postCategory;

    public PostResponseDto(Post post){
        this.memberResponseDto = post.toMemberResponseDto();
        this.postNumber = post.getPostNumber();;
        this.postCategory = post.getPostCategory();
        this.postTitle = post.getPostTitle();
        this.postContents = post.getPostContents();
    }

}
