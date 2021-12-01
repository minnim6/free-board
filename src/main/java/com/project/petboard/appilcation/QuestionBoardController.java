package com.project.petboard.appilcation;

import com.project.petboard.domain.questionboard.QuestionBoard;
import com.project.petboard.domain.questionboard.QuestionBoardDto;
import com.project.petboard.domain.questionboard.QuestionBoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/questionBoard")
@RestController
public class QuestionBoardController {

    private final QuestionBoardService questionBoardService;

    @GetMapping(value = "/fetchQuestionBoard")
    public QuestionBoardDto fetchQuestionBoard(@RequestParam("questionNumber")Long questionNumber){
        return questionBoardService.fetchQuestionBoard(questionNumber);
    }
    @DeleteMapping(value = "/delete")
    public void deleteQuestionBoard(@RequestParam("questionNumber")Long questionNumber){
        questionBoardService.deleteQuestionBoard(questionNumber);
    }
    @PostMapping(value = "/create")
    public void createQuestionBoard(@RequestBody QuestionBoardDto questionBoardDto){
        questionBoardService.createQuestionBoard(questionBoardDto);
    }
    @GetMapping(value = "/getPage")
    public Page<QuestionBoard> requestPage(Pageable pageable){
        return questionBoardService.requestPage(pageable);
    }
}
