package com.project.petboard.domain.questionboard;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class QuestionBoardAnswerRequestDto {
   private Long questionBoardNumber;
   private String questionBoardAnswer;

   public QuestionBoardAnswerRequestDto(Long questionBoardNumber,String questionBoardAnswer){
      this.questionBoardNumber = questionBoardNumber;
      this.questionBoardAnswer = questionBoardAnswer;
   }
}
