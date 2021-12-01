package com.project.petboard.appilcation;

import com.project.petboard.domain.post.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/getPage")
    public Page<Post> requestPage(Pageable pageable){
        return postService.requestPage(pageable);
    }

    @PostMapping
    public void createPost(@RequestBody PostDto postDto){
        postService.createPost(postDto);
    }

    @DeleteMapping
    public void deletePost(@RequestParam("postNumber")Long postNumber){
        postService.deletePost(postNumber);
    }

    @GetMapping("/fetchPost")
    public PostDto fetchPost(@RequestParam("postNumber") Long postNumber){
        return postService.fetchPost(postNumber);
    }
}
