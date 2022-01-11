package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.CommentController;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.comment.CommentResponseDto;
import com.project.petboard.domain.comment.CommentService;
import com.project.petboard.domain.member.MemberResponseDto;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.request.CommentRequestTestDto;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

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
@WebMvcTest(value = CommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class})})
public class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private CommentService commentService;

    private CommentResponseDto commentResponseDto;

    @BeforeEach
    public void setup() {
        MemberResponseDto memberDto = new MemberResponseDto(1L, "nickname");
        commentResponseDto = new CommentResponseDto(1L, memberDto, "대대대대대댓글", new Date());
    }


    @WithMockUser(roles = "MEMBER")
    @DisplayName("댓글 생성 테스트")
    @Test
    public void createCommentTestShouldBeSuccess() throws Exception {

        mockMvc.perform(post("/comment")
                        .content(objectMapper.writeValueAsString(new CommentRequestTestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(document("comment/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("memberNumber").type(JsonFieldType.NUMBER).description("회원 번호"),
                                fieldWithPath("postNumber").type(JsonFieldType.NUMBER).description("게시물 번호"),
                                fieldWithPath("commentContents").type(JsonFieldType.STRING).description("게시물 내용")
                        )));
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("댓글 삭제 테스트")
    @Test
    public void deleteCommentTestShouldBeSuccess() throws Exception {
        mockMvc.perform(delete("/comment")
                .param("commentNumber",String.valueOf(1L)))
                .andExpect(status().isOk())
                .andDo(document("comment/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("commentNumber").description("댓글 번호")
                        )));
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("댓글 생성실패 테스트")
    @Test
    public void createCommentTestShouldBeFail() throws Exception {
        mockMvc.perform(post("/comment")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("댓글 페이지 가져오기 테스트")
    @Test
    public void getCommentPageTestShouldBeSuccess() throws Exception {

        Map<String, Object> requestPage = new HashMap<>();

        requestPage.put("page", 0);
        requestPage.put("size", 10);

        List<CommentResponseDto> listDto = new ArrayList<>();
        listDto.add(commentResponseDto);
        listDto.add(commentResponseDto);

        doReturn(listDto).when(commentService).getCommentPage(any());

        mockMvc.perform(get("/comment/page")
                        .content(objectMapper.writeValueAsString(requestPage))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(document("comment/get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("page").description("페이지 번호"),
                                fieldWithPath("size").description("한페이지당 게시물 갯수")
                        ),
                        responseFields(
                                fieldWithPath("[].commentNumber").description("댓글 고유번호"),
                                fieldWithPath("[].commentContents").description("내용"),
                                fieldWithPath("[].commentCreateDate").description("작성날짜"),
                                fieldWithPath("[].memberResponseDto.memberNumber").description("회원 고유 번호"),
                                fieldWithPath("[].memberResponseDto.memberNickname").description("회원 닉네임")
                        )
                ));
    }

}
