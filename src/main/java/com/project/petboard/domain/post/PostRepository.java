package com.project.petboard.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>  {

    @EntityGraph(attributePaths = "member",type = EntityGraph.EntityGraphType.LOAD)
    Post findByPostNumber(Long postNumber);

    @EntityGraph(attributePaths = "member",type = EntityGraph.EntityGraphType.LOAD)
    Page<Post> findAllByPostStatus(Pageable pageable,PostStatus postStatus);
}
