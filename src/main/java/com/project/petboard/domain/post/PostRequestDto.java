package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@NotNull
@NoArgsConstructor
@Getter
public class PostRequestDto {

    @Positive(message = "필수로 요소가 필요합니다.")
    private Long memberNumber;

    @NotBlank(message = "필수로 요소가 필요합니다.")
    @Size(min = 2,max = 25,message = "최소 2글자 이상 , 최대 25글자 미만입니다.")
    private String postTitle;

    @NotBlank(message = "필수로 요소가 필요합니다.")
    @Size(min = 2,max = 450,message = "최소 2글자 이상 , 최대 450글자 미만입니다.")
    private String postContents;

    @NotBlank(message = "필수로 요소가 필요합니다.")
    @Size(min = 2,max = 20,message = "최소 2글자 이상 , 최대 20글자 미만입니다.")
    private String postCategory;

    public Post toEntity(Member member){
        return Post.builder()
                .member(member)
                .postTitle(postTitle)
                .postCategory(postCategory)
                .postContents(postContents)
                .build();
    }
}
