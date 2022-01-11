package com.project.petboard.appilcation;

import com.project.petboard.domain.page.RequestPage;
import com.project.petboard.domain.questionboard.QuestionBoardAnswerRequestDto;
import com.project.petboard.domain.questionboard.QuestionBoardRequestDto;
import com.project.petboard.domain.questionboard.QuestionBoardService;
import com.project.petboard.domain.questionboard.QuestionResponseDto;
import com.project.petboard.infrastructure.exception.RequestErrorException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class QuestionBoardController {

    private final QuestionBoardService questionBoardService;

    @GetMapping(value = "/question")
    public QuestionResponseDto fetchQuestionBoard(@RequestParam("questionNumber")Long questionNumber){
        return questionBoardService.fetchQuestionBoard(questionNumber);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/question")
    public void deleteQuestionBoard(@RequestParam("questionNumber")Long questionNumber){
        questionBoardService.deleteQuestionBoard(questionNumber);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/question/answer")
    public void questionBoardAnswer(@RequestBody @Valid QuestionBoardAnswerRequestDto answerRequestDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestErrorException(bindingResult);
        }questionBoardService.questionBoardAnswer(answerRequestDto);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping(value = "/question")
    public QuestionResponseDto createQuestionBoard(@RequestBody @Valid QuestionBoardRequestDto questionBoardDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestErrorException(bindingResult);
        }return questionBoardService.createQuestionBoard(questionBoardDto);
    }

    @GetMapping(value = "/question/page")
    public List<QuestionResponseDto> requestPage(@RequestBody RequestPage requestPage){
        return questionBoardService.requestPage(requestPage);
    }
}
