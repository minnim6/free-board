package com.project.petboard.appilcation;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRequestDto;
import com.project.petboard.domain.comment.CommentResponseDto;
import com.project.petboard.domain.comment.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.createComment(commentRequestDto);
    }

    @DeleteMapping("/comment")
    public void deleteComment(@RequestParam("commentNumber")Long commentNumber) {
        commentService.deleteComment(commentNumber);
    }
    @GetMapping("/comment/page")
    public Page<CommentResponseDto> fetchCommentPage(Pageable pageable) {
       return commentService.fetchCommentPage(pageable);
    }
}
