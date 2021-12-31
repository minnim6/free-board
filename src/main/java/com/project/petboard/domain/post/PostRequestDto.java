package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
public class PostRequestDto {

    private final Long memberNumber;

    private final String postTitle;

    private final String postContents;

    private final String postCategory;

    public Post toEntity(Member member){
        return Post.builder()
                .member(member)
                .postTitle(postTitle)
                .postCategory(postCategory)
                .postContents(postContents)
                .build();
    }
}
