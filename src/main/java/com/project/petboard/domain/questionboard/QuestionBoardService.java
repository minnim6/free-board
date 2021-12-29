package com.project.petboard.domain.questionboard;

import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionBoardService {

    private final QuestionBoardRepository questionBoardRepository;

    @Transactional(readOnly = true)
    public QuestionBoardDto fetchQuestionBoard(Long questionNumber) {
        try {
            return new QuestionBoardDto(questionBoardRepository.findByQuestionBoardNumber(questionNumber));
        } catch (Exception e) {
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.NOT_FOUND);
        }
    }

    public void deleteQuestionBoard(Long questionNumber) {
        questionBoardRepository.deleteById(questionNumber);
    }

    public Long createQuestionBoard(QuestionBoardDto questionBoardDto) {
        return questionBoardRepository.save(questionBoardDto.toEntity()).getQuestionBoardNumber();
    }

    @Transactional(readOnly = true)
    public Page<QuestionBoard> requestPage(Pageable pageable) {
        try {
            return questionBoardRepository.findAll(pageable);
        } catch (Exception e) {
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.NOT_FOUND);
        }
    }
}
