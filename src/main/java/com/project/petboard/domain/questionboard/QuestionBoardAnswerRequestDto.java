package com.project.petboard.domain.questionboard;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NotNull
@NoArgsConstructor
@Getter
public class QuestionBoardAnswerRequestDto {

   @Positive(message = "필수로 요소가 필요합니다.")
   private Long questionBoardNumber;

   @Size(min = 10,max = 450,message = "최소 10글자 이상 , 최대 450글자 미만입니다.")
   private String questionBoardAnswer;

   public QuestionBoardAnswerRequestDto(Long questionBoardNumber,String questionBoardAnswer){
      this.questionBoardNumber = questionBoardNumber;
      this.questionBoardAnswer = questionBoardAnswer;
   }
}
