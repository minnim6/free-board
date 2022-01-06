package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NotNull
@NoArgsConstructor
@Getter
public class QuestionBoardRequestDto {

    @Positive(message = "필수로 요소가 필요합니다.")
    private Long memberNumber;

    @NotBlank
    @Size(min = 2,max = 20,message = "최소 2글자 이상 , 최대 20글자 미만입니다.")
    private String questionBoardTitle;

    @NotBlank
    @Size(min = 20,max = 450,message = "최소 20글자 이상 , 최대 450글자 미만입니다.")
    private String questionBoardContents;

    public QuestionBoard toEntity(Member member){
        return  QuestionBoard.builder()
                .member(member)
                .questionBoardTitle(questionBoardTitle)
                .questionBoardContents(questionBoardContents)
                .build();
    }
}
