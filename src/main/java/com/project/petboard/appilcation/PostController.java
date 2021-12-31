package com.project.petboard.appilcation;

import com.project.petboard.domain.post.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(value = "/post")
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/getPage")
    public Page<Post> requestPage(Pageable pageable) {
        return postService.requestPage(pageable);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MEMBER')")
    public Long createPost(@RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('MEMBER')")
    public void deletePost(@RequestParam("postNumber")Long postNumber) {
        postService.deletePost(postNumber);
    }

    @GetMapping("/getPost")
    public PostResponseDto fetchPost(@RequestParam("postNumber") Long postNumber){
            return postService.fetchPost(postNumber);
    }

    @PostMapping("/visibleChangePostStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public void visibleChangePostStatus(@RequestParam("postNumber")Long postNumber) {
        postService.visibleChangePostStatus(postNumber);
    }
}
