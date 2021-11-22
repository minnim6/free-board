package com.project.petboard.appilcation;

import com.project.petboard.domain.post.*;
import com.project.petboard.domain.report.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping
    public Page<Post> requestPage(final Pageable pageable){
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

    @GetMapping("{postNumber}")
    public void requestPost(@RequestParam("postNumber")Long postNumber){
       // return postService.requestPost(postNumber);
    }

    @PostMapping("/report")
    public void reportPost(@RequestBody ReportDto reportDto){
        postService.reportPost(reportDto);
    }
}
