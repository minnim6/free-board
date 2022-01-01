package com.project.petboard.controller;


import com.project.petboard.appilcation.PostController;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostResponseDto;
import com.project.petboard.domain.post.PostService;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    PostService postService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @Test
    public void fetchPostTest() throws Exception {

        Long postId = 1L;

        final String title = "타이틀입니다.";
        final String content = "내용입니다";

        Post post = Post.builder()
                .postTitle(title)
                .postContents(content)
                .member(new Member())
                .build();

        doReturn(new PostResponseDto(post)).when(postService).fetchPost(postId);

        ResultActions actions = mockMvc.perform(get("/post")
                .param("postNumber", String.valueOf(postId)));

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.postTitle").value(title))
                .andExpect(jsonPath("$.postContents").value(content));

    }

}
