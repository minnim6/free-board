package com.project.petboard.appilcation;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberDto;
import com.project.petboard.domain.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public Page<Post> requestPostPage(final Pageable pageable){
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
