package com.project.petboard.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void createComment(CommentDto commentDto) {
        commentRepository.save(commentDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<Comment> fetchCommentPage(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public void deleteComment(Long commentNumber) {
        commentRepository.deleteById(commentNumber);
    }

}
