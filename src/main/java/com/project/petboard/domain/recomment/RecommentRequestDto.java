package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class RecommentRequestDto {

    private Long postNumber;

    private Long memberNumber;

    private Long commentNumber;

    private String recommentContents;

    public Recomment toEntity(Post post, Member member,Comment comment){
        return Recomment.builder()
                .member(member)
                .post(post)
                .comment(comment)
                .recommentContents(recommentContents)
                .build();
    }
}
