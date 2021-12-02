package com.project.petboard.appilcation;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentDto;
import com.project.petboard.domain.comment.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/Comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/createComment")
    public void createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
    }
    @DeleteMapping(value = "deleteComment")
    public void deleteComment(@RequestParam("commentNumber")Long commentNumber) {
        commentService.deleteComment(commentNumber);
    }
    @GetMapping(value = "getCommentPage")
    public Page<Comment> fetchCommentPage(Pageable pageable) {
       return commentService.fetchCommentPage(pageable);
    }
}
