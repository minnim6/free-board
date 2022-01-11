package com.project.petboard.appilcation;

import com.project.petboard.domain.comment.CommentRequestDto;
import com.project.petboard.domain.comment.CommentResponseDto;
import com.project.petboard.domain.comment.CommentService;
import com.project.petboard.domain.page.RequestPage;
import com.project.petboard.infrastructure.exception.RequestErrorException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody @Valid CommentRequestDto commentRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new RequestErrorException(bindingResult);
        }return commentService.createComment(commentRequestDto);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping("/comment")
    public void deleteComment(@RequestParam("commentNumber")Long commentNumber) {
        commentService.deleteComment(commentNumber);
    }
    @GetMapping("/comment/page")
    public List<CommentResponseDto> getCommentPage(@RequestBody RequestPage requestPage) {
       return commentService.getCommentPage(requestPage);
    }
}
