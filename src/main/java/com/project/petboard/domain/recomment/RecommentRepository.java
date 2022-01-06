package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommentRepository extends JpaRepository<Recomment,Long> {

    @EntityGraph(attributePaths = {"member","comment"},type = EntityGraph.EntityGraphType.LOAD)
    Recomment findByRecommentNumber(Long recommentNumber);
    @EntityGraph(attributePaths = {"member","comment"},type = EntityGraph.EntityGraphType.LOAD)
    Page<Recomment> findAll(Pageable pageable);

    void deleteAllByPost(Post post);

    void deleteAllByComment(Comment comment);
}
