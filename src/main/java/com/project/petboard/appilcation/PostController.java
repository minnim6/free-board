package com.project.petboard.appilcation;

import com.project.petboard.domain.post.PostRequestDto;
import com.project.petboard.domain.post.PostResponseDto;
import com.project.petboard.domain.post.PostService;
import com.project.petboard.infrastructure.exception.RequestErrorException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/post/page")
    public Page<PostResponseDto> requestPage(Pageable pageable) {
        return postService.getPostPage(pageable);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/post")
    public Long createPost(@RequestBody @Valid PostRequestDto postRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new RequestErrorException(bindingResult);
        }return postService.createAndUpdatePost(postRequestDto);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PatchMapping("/post")
    public Long updatePost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createAndUpdatePost(postRequestDto);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping("/post")
    public void deletePost(@RequestParam("postNumber") Long postNumber) {
        postService.deletePost(postNumber);
    }

    @GetMapping("/post")
    public PostResponseDto fetchPost(@RequestParam("postNumber") Long postNumber){
            return postService.fetchPost(postNumber);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/post/status")
    public void visibleChangePostStatus(@RequestParam("postNumber") Long postNumber) {
        postService.visibleChangePostStatus(postNumber);
    }
}
