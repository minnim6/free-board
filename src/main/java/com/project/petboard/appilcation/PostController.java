package com.project.petboard.appilcation;

import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostDto;
import com.project.petboard.domain.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Api(value = "requestPostController")
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @ApiOperation(value = "페이지 조회" , notes = "요청된 페이지에 해당하는 페이지를 리턴 합니다.")
    @GetMapping
    public Page<Post> requestPostPage(final Pageable pageable){
        return postService.requestPageBoard(pageable);
    }

    @ApiOperation(value = "게시물 작성")
    @PostMapping
    public void createPost(@RequestBody PostDto postDto){
        postService.createPostBoard(postDto);
    }

    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping
    public void deletePost(@RequestParam("postNumber")Long postNumber){

    }


}
