package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NotNull
@NoArgsConstructor
@Getter
public class RecommentRequestDto {

    @Positive(message = "필수로 요소가 필요합니다.")
    private Long postNumber;

    @Positive(message = "필수로 요소가 필요합니다.")
    private Long memberNumber;

    @Positive(message = "필수로 요소가 필요합니다.")
    private Long commentNumber;

    @NotBlank
    @Size(min = 1,max = 80,message = "최소 1글자 이상 , 최대 80글자 미만입니다.")
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
