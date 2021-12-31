package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class RecommentRequestDto {

    private final Long postNumber;

    private final Long memberNumber;

    private final Long commentNumber;

    private final String recommentContents;

    public Recomment toEntity(Post post, Member member,Comment comment){
        return Recomment.builder()
                .member(member)
                .post(post)
                .comment(comment)
                .recommentContents(recommentContents)
                .build();
    }
}
