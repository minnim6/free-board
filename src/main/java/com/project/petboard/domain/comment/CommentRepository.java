package com.project.petboard.domain.comment;

import com.project.petboard.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findByCommentNumber(Long commentNumber);
    void deleteAllByPost(Post post);
}
