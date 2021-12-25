package com.project.petboard.appilcation;

import com.project.petboard.domain.member.MemberRequestDto;
import com.project.petboard.domain.post.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public void createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('MEMBER')")
    public void deletePost(@RequestParam("postNumber")Long postNumber) {
        postService.deletePost(postNumber);
    }

    @GetMapping("/getPost")
    public PostDto fetchPost(@RequestParam("postNumber") Long postNumber) {
        return postService.fetchPost(postNumber);
    }

    @PostMapping("/visibleChangePostStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public void visibleChangePostStatus(@RequestParam("postNumber")Long postNumber) {
        postService.visibleChangePostStatus(postNumber);
    }
}
