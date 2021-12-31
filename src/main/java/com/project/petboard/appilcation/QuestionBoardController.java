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
@RequestMapping("/questionBoard")
@RestController
public class QuestionBoardController {

    private final QuestionBoardService questionBoardService;

    @GetMapping(value = "/getQuestionBoard")
    public QuestionResponseDto fetchQuestionBoard(@RequestParam("questionNumber")Long questionNumber){
        return questionBoardService.fetchQuestionBoard(questionNumber);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/delete")
    public void deleteQuestionBoard(@RequestParam("questionNumber")Long questionNumber){
        questionBoardService.deleteQuestionBoard(questionNumber);
    }

    @PostMapping(value = "/create")
    public Long createQuestionBoard(@RequestBody QuestionBoardRequestDto questionBoardDto){
        return questionBoardService.createQuestionBoard(questionBoardDto);
    }
    @GetMapping(value = "/getPage")
    public Page<QuestionBoard> requestPage(Pageable pageable){
        return questionBoardService.requestPage(pageable);
    }
}
