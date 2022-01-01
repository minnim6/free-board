package com.project.petboard.appilcation;

import com.project.petboard.domain.post.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/post/page")
    public Page<Post> requestPage(Pageable pageable) {
        return postService.requestPage(pageable);
    }

    @PostMapping("/post")
    @PreAuthorize("hasRole('MEMBER')")
    public Long createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    @DeleteMapping("/post")
    @PreAuthorize("hasRole('MEMBER')")
    public void deletePost(@RequestParam("postNumber")Long postNumber) {
        postService.deletePost(postNumber);
    }

    @GetMapping("/post")
    public PostResponseDto fetchPost(@RequestParam("postNumber") Long postNumber){
            return postService.fetchPost(postNumber);
    }

    @PostMapping("/post/status")
    @PreAuthorize("hasRole('ADMIN')")
    public void visibleChangePostStatus(@RequestParam("postNumber")Long postNumber) {
        postService.visibleChangePostStatus(postNumber);
    }
}
