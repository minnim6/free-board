package com.project.petboard.domain.comment;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
public class CommentRequestDto {

    private Long postNumber;

    private Long memberNumber;

    private String commentContents;

    public Comment toEntity(Post post,Member member){
        return Comment
                .builder()
                .post(post)
                .member(member)
                .commentContents(commentContents)
                .build();
    }

}
