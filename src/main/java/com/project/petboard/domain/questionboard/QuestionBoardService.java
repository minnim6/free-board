package com.project.petboard.domain.questionboard;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Service
public class QuestionBoardService {

    private final QuestionBoardRepository questionBoardRepository;

    public QuestionBoardDto fetchQuestionBoard(Long questionNumber){
        return new QuestionBoardDto(questionBoardRepository.findByQuestionBoardNumber(questionNumber));
    }
    public void deleteQuestionBoard(Long questionNumber){
        questionBoardRepository.deleteById(questionNumber);
    }
    public void createQuestionBoard(QuestionBoardDto questionBoardDto){
        questionBoardRepository.save(questionBoardDto.toEntity());
    }
    public Page<QuestionBoard> requestPage(Pageable pageable){
       return questionBoardRepository.findAll(pageable);
    }
}
