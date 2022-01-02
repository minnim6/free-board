package com.project.petboard.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.PostController;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.*;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import org.hibernate.sql.Delete;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private PostService postService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    private final Long postId = 1L;

    @DisplayName("게시물 가져오기 테스트")
    @Test
    public void fetchPostTestShouldBeSuccess() throws Exception {

        String title = "title";
        String content = "contents";

        Post post = Post.builder()
                .postTitle(title)
                .postContents(content)
                .member(new Member())
                .build();

        doReturn(new PostResponseDto(post)).when(postService).fetchPost(postId);

        mockMvc.perform(get("/post")
                .param("postNumber", String.valueOf(postId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postTitle").value(title))
                .andExpect(jsonPath("$.postContents").value(content));

    }

    @DisplayName("게시물 삭제 테스트")
    @Test
    public void deletePostTestShouldBeSuccess() throws Exception {

        mockMvc.perform(delete("/post")
                .param("postNumber", String.valueOf(postId)))
                .andExpect(status().isOk());

        assertThat(postRepository.findByPostNumber(1L)).isNull();
    }

    @DisplayName("게시물 수정 테스트")
    @Test
    public void updatePostTestShouldBeSuccess() throws Exception {

        Map<String,Object> postRequestDto = new HashMap<>();

        postRequestDto.put("memberNumber", 1);
        postRequestDto.put("postTitle", "title");
        postRequestDto.put("postContents", "contents");
        postRequestDto.put("postCategory", "category");

        mockMvc.perform(patch("/post")
                .content(objectMapper.writeValueAsString(postRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @DisplayName("질문게시글 생성 테스트")
    @Test
    public void createPostTestShouldBeSuccess() throws Exception {

        Map<String,Object> postRequestDto = new HashMap<>();

        postRequestDto.put("memberNumber", 1);
        postRequestDto.put("postTitle", "title");
        postRequestDto.put("postContents", "contents");
        postRequestDto.put("postCategory", "category");

        mockMvc.perform(post("/post")
                        .content(objectMapper.writeValueAsString(postRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("게시물 상태변경 테스트")
    @Test
    public void postStatusChangeTestShouldBeSuccess() throws Exception {

        mockMvc.perform(patch("/post/status")
                .param("postNumber", String.valueOf(postId))).andExpect(status().isOk());

    }

}
