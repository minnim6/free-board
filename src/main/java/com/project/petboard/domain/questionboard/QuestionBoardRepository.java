package com.project.petboard.domain.questionboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard,Long> {
    QuestionBoard findByQuestionBoardNumber(Long questionNumber);
}
