package com.project.petboard.appilcation;

import com.project.petboard.domain.page.RequestPage;
import com.project.petboard.domain.post.PostRequestDto;
import com.project.petboard.domain.post.PostResponseDto;
import com.project.petboard.domain.post.PostService;
import com.project.petboard.infrastructure.exception.RequestErrorException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/post/page")
    public List<PostResponseDto> requestPage(@RequestBody RequestPage requestPage) {
        return postService.getPostPage(requestPage);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody @Valid PostRequestDto postRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new RequestErrorException(bindingResult);
        }return postService.savePost(postRequestDto);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PatchMapping("/post")
    public PostResponseDto updatePost(@RequestBody @Valid PostRequestDto postRequestDto,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new RequestErrorException(bindingResult);
        }
        return postService.updatePost(postRequestDto);
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
