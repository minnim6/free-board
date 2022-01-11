package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.post.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommentRepository extends JpaRepository<Recomment,Long> {

    @EntityGraph(attributePaths = {"member","comment"},type = EntityGraph.EntityGraphType.LOAD)
    Recomment findByRecommentNumber(Long recommentNumber);
    @EntityGraph(attributePaths = {"member","comment"},type = EntityGraph.EntityGraphType.LOAD)
    List<Recomment> findAllByComment(Comment comment);

    void deleteAllByPost(Post post);

    void deleteAllByComment(Comment comment);
}
