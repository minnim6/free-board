package com.project.petboard.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.PostController;
import com.project.petboard.domain.member.MemberResponseDto;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.post.PostResponseDto;
import com.project.petboard.domain.post.PostService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.request.PostCreateRequestTestDto;
import com.project.petboard.request.PostUpdateRequestTestDto;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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

    private PostResponseDto postResponseDto;

    private final Long postId = 1L;

    @BeforeEach
    public void setup() {
        String title = "title";
        String content = "contents";
        String category = "category";
        MemberResponseDto memberDto = new MemberResponseDto(1L, "nickname");
        postResponseDto = new PostResponseDto(1L, memberDto, title, content, category);
    }

    @DisplayName("게시물 가져오기 테스트")
    @Test
    public void fetchPostTestShouldBeSuccess() throws Exception {

        doReturn(postResponseDto).when(postService).fetchPost(postId);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/post")
                        .param("postNumber", String.valueOf(postId)))
                .andExpect(status().isOk())
                .andDo(document("post/fetch",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("postNumber").description("게시물 번호")
                        ),

                        responseFields(
                                fieldWithPath("postNumber").description("고유번호"),
                                fieldWithPath("postTitle").description("제목"),
                                fieldWithPath("postContents").description("내용"),
                                fieldWithPath("postCategory").description("카테고리"),
                                fieldWithPath("memberResponseDto.memberNumber").description("회원 고유 번호"),
                                fieldWithPath("memberResponseDto.memberNickname").description("회원 닉네임")
                        )

                ));

    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("게시물 삭제 테스트")
    @Test
    public void deletePostTestShouldBeSuccess() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/post")
                        .param("postNumber", String.valueOf(postId)))
                .andExpect(status().isOk())
                .andDo(document("post/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("postNumber").description("게시물 번호")
                        )));

        assertThat(postRepository.findByPostNumber(1L)).isNull();
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("게시물 수정 테스트")
    @Test
    public void updatePostTestShouldBeSuccess() throws Exception {
        doReturn(postResponseDto).when(postService).updatePost(any());

        mockMvc.perform(RestDocumentationRequestBuilders.patch("/post")
                        .content(objectMapper.writeValueAsString(new PostUpdateRequestTestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("postNumber").type(JsonFieldType.NUMBER).description("게시물 번호"),
                                fieldWithPath("memberNumber").type(JsonFieldType.NUMBER).description("회원 번호"),
                                fieldWithPath("postTitle").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("postContents").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("postCategory").type(JsonFieldType.STRING).description("게시물 카테고리")
                        ),
                        responseFields(
                                fieldWithPath("postNumber").description("게시물 고유번호"),
                                fieldWithPath("postTitle").description("제목"),
                                fieldWithPath("postContents").description("내용"),
                                fieldWithPath("postCategory").description("카테고리"),
                                fieldWithPath("memberResponseDto.memberNumber").description("회원 고유 번호"),
                                fieldWithPath("memberResponseDto.memberNickname").description("회원 닉네임")
                        )
                ));

    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("게시글 생성 테스트")
    @Test
    public void createPostTestShouldBeSuccess() throws Exception {

        doReturn(postResponseDto).when(postService).savePost(any());

        mockMvc.perform(RestDocumentationRequestBuilders.post("/post")
                        .content(objectMapper.writeValueAsString(new PostCreateRequestTestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("memberNumber").type(JsonFieldType.NUMBER).description("회원 번호"),
                                fieldWithPath("postTitle").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("postContents").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("postCategory").type(JsonFieldType.STRING).description("게시물 카테고리")
                        ),
                        responseFields(
                                fieldWithPath("postNumber").description("게시물 고유번호"),
                                fieldWithPath("postTitle").description("제목"),
                                fieldWithPath("postContents").description("내용"),
                                fieldWithPath("postCategory").description("카테고리"),
                                fieldWithPath("memberResponseDto.memberNumber").description("회원 고유 번호"),
                                fieldWithPath("memberResponseDto.memberNickname").description("회원 닉네임")
                        )
                ));
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("게시물 상태변경 테스트")
    @Test
    public void postStatusChangeTestShouldBeSuccess() throws Exception {

        mockMvc.perform(patch("/post/status")
                        .param("postNumber", String.valueOf(postId))).andExpect(status().isOk())
                .andDo(document("post/status",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("postNumber").description("게시물 번호")
                        )));

    }

    @DisplayName("게시물 페이지 가져오기 테스트")
    @Test
    public void getPostPageTestShouldBeSuccess() throws Exception {

        Map<String, Object> requestPage = new HashMap<>();

        requestPage.put("page", 0);
        requestPage.put("size", 10);

        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(postResponseDto);
        listDto.add(postResponseDto);

        doReturn(listDto).when(postService).getPostPage(any());

        mockMvc.perform(get("/post/page")
                        .content(objectMapper.writeValueAsString(requestPage))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(document("post/page",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("page").description("페이지 번호"),
                                fieldWithPath("size").description("한페이지당 게시물 갯수")
                        ),
                        responseFields(
                                fieldWithPath("[].postNumber").description("게시물 고유번호"),
                                fieldWithPath("[].postTitle").description("제목"),
                                fieldWithPath("[].postContents").description("내용"),
                                fieldWithPath("[].postCategory").description("카테고리"),
                                fieldWithPath("[].memberResponseDto.memberNumber").description("회원 고유 번호"),
                                fieldWithPath("[].memberResponseDto.memberNickname").description("회원 닉네임")
                        )
                ));
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("질문게시글 생성 실패 테스트")
    @Test
    public void createPostTestShouldBeFail() throws Exception{
        mockMvc.perform(post("/post")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


}
