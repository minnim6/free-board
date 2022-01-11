package com.project.petboard.domain.questionboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard,Long> {

    @EntityGraph(attributePaths = "member",type = EntityGraph.EntityGraphType.LOAD)
    QuestionBoard findByQuestionBoardNumber(Long questionNumber);

    @EntityGraph(attributePaths = "member",type = EntityGraph.EntityGraphType.LOAD)
    Page<QuestionBoard> findAll(Pageable pageable);
}
