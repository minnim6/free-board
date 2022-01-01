package com.project.petboard.appilcation;

import com.project.petboard.domain.questionboard.QuestionBoard;
import com.project.petboard.domain.questionboard.QuestionBoardRequestDto;
import com.project.petboard.domain.questionboard.QuestionBoardService;
import com.project.petboard.domain.questionboard.QuestionResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/question")
    public Long createQuestionBoard(@RequestBody QuestionBoardRequestDto questionBoardDto){
        return questionBoardService.createQuestionBoard(questionBoardDto);
    }
    @GetMapping(value = "/question/page")
    public Page<QuestionBoard> requestPage(Pageable pageable){
        return questionBoardService.requestPage(pageable);
    }
}
