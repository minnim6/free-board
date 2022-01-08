package com.project.petboard.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.PostController;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.post.PostResponseDto;
import com.project.petboard.domain.post.PostService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(value = PostController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private PostService postService;

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

        mockMvc.perform(RestDocumentationRequestBuilders.get("/post")
                        .param("postNumber", String.valueOf(postId)))
                        .andExpect(status().isOk())
                        .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("postNumber").description("post number")
                        )
                ));

    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("게시물 삭제 테스트")
    @Test
    public void deletePostTestShouldBeSuccess() throws Exception {

        mockMvc.perform(delete("/post")
                        .param("postNumber", String.valueOf(postId)))
                .andExpect(status().isOk());

        assertThat(postRepository.findByPostNumber(1L)).isNull();
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("게시물 수정 테스트")
    @Test
    public void updatePostTestShouldBeSuccess() throws Exception {

        Map<String, Object> postRequestDto = new HashMap<>();

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

    @WithMockUser(roles = "MEMBER")
    @DisplayName("게시글 생성 테스트")
    @Test
    public void createPostTestShouldBeSuccess() throws Exception {

        Map<String, Object> postRequestDto = new HashMap<>();

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

    @WithMockUser(roles = "MEMBER")
    @DisplayName("질문게시글 생성 실패 테스트")
    @Test
    public void createPostTestShouldBeFail() throws Exception {

        Map<String, Object> postRequestDto = new HashMap<>();

        postRequestDto.put("memberNumber", 1);
        postRequestDto.put("postTitle", "");
        postRequestDto.put("postContents", "contents");
        postRequestDto.put("postCategory", "category");

        mockMvc.perform(post("/post")
                        .content(objectMapper.writeValueAsString(postRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("게시물 상태변경 테스트")
    @Test
    public void postStatusChangeTestShouldBeSuccess() throws Exception {

        mockMvc.perform(patch("/post/status")
                .param("postNumber", String.valueOf(postId))).andExpect(status().isOk());

    }

    @DisplayName("게시물 페이지 가져오기 테스트")
    @Test
    public void getPostPageTestShouldBeSuccess() throws Exception {
        mockMvc.perform(get("/post/page")
                        .param("page", String.valueOf(0)))
                .andExpect(status().isOk());
    }

}
