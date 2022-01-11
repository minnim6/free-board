package com.project.petboard.domain.comment;

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
public class CommentRequestDto {

    @NotNull(message = "필수로 요소가 필요합니다.")
    @Positive(message = "유효하지 않은 숫자입니다.")
    private Long postNumber;

    @NotNull(message = "필수로 요소가 필요합니다.")
    @Positive(message = "유효하지 않은 숫자입니다.")
    private Long memberNumber;

    @NotBlank(message = "빈문자가 들어올수 없습니다.")
    @Size(min = 1 , max = 100,message = "최소 1글자 이성 작성이 필요합니다.")
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
