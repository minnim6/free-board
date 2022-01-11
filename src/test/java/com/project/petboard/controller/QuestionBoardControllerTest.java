package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.QuestionBoardController;
import com.project.petboard.domain.member.MemberResponseDto;
import com.project.petboard.domain.questionboard.QuestionBoardRepository;
import com.project.petboard.domain.questionboard.QuestionBoardService;
import com.project.petboard.domain.questionboard.QuestionResponseDto;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.request.QuestionAnswerRequestTestDto;
import com.project.petboard.request.QuestionRequestTestDto;
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
@WebMvcTest(value = QuestionBoardController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class QuestionBoardControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionBoardRepository questionBoardRepository;

    @MockBean
    private QuestionBoardService questionBoardService;

    private final Long questionBoardNumber = 1L;

    private QuestionResponseDto questionResponseDto;

    @BeforeEach
    public void setup(){
        String title = "title";
        String content = "contents";

        MemberResponseDto memberDto = new MemberResponseDto(1L, "nickname");
        questionResponseDto = new QuestionResponseDto(
                1L, memberDto, title, content, new Date(), null, null
        );
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("질문 게시글 가져오기 테스트")
    @Test
    public void fetchQuestionBoardTestShouldBeSuccess() throws Exception {

        doReturn(questionResponseDto).when(questionBoardService).fetchQuestionBoard(questionBoardNumber);

        mockMvc.perform(get("/question")
                        .param("questionNumber", String.valueOf(questionBoardNumber)))
                .andExpect(status().isOk())
                .andDo(document("question/fetch",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("questionNumber").description("질문게시물 번호")
                        ),

                        responseFields(
                                fieldWithPath("questionBoardNumber").type(JsonFieldType.NUMBER).description("고유번호"),
                                fieldWithPath("questionBoardTitle").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("questionBoardContents").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("questionBoardCreateDate").type("Date").description("작성날짜"),
                                fieldWithPath("questionBoardAnswerDate").type("Date").description("답변날짜").optional(),
                                fieldWithPath("questionBoardAnswer").type(JsonFieldType.STRING).description("답변내용").optional(),
                                fieldWithPath("memberResponseDto.memberNumber").type(JsonFieldType.NUMBER).description("회원 고유 번호"),
                                fieldWithPath("memberResponseDto.memberNickname").type(JsonFieldType.STRING).description("회원 닉네임")
                        )

                ));
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("질문 게시글 삭제 테스트")
    @Test
    public void deleteQuestionBoardTestShouldBeSuccess() throws Exception {

        mockMvc.perform(delete("/question")
                        .param("questionNumber", String.valueOf(questionBoardNumber)))
                .andExpect(status().isOk())
                .andDo(document("question/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("questionNumber").description("게시물 번호")
                        )));

        assertThat(questionBoardRepository.findByQuestionBoardNumber(questionBoardNumber)).isNull();
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("질문 답변 테스트")
    @Test
    public void QuestionBoardAnswerTestShouldBeSuccess() throws Exception {
        mockMvc.perform(patch("/question/answer")
                        .content(objectMapper.writeValueAsString(new QuestionAnswerRequestTestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("question/answer",
                preprocessRequest(prettyPrint()),
                requestFields(
                        fieldWithPath("questionBoardNumber").type(JsonFieldType.NUMBER).description("질문글 번호"),
                        fieldWithPath("questionBoardContents").description("답변")
                )));
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("질문 답변 실패 테스트")
    @Test
    public void QuestionBoardAnswerTestShouldBeFail() throws Exception {

        mockMvc.perform(patch("/question/answer")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("질문게시글 생성 테스트")
    @Test
    public void createQuestionBoardTestShouldBeSuccess() throws Exception {

        doReturn(questionResponseDto).when(questionBoardService).createQuestionBoard(any());

        mockMvc.perform(post("/question")
                        .content(objectMapper.writeValueAsString(new QuestionRequestTestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("question/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("questionBoardTitle").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("questionBoardContents").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("memberNumber").type(JsonFieldType.NUMBER).description("회원 고유 번호")
                        ),

                        responseFields(
                                fieldWithPath("questionBoardNumber").type(JsonFieldType.NUMBER).description("고유번호"),
                                fieldWithPath("questionBoardTitle").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("questionBoardContents").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("questionBoardCreateDate").type("Date").description("작성날짜"),
                                fieldWithPath("questionBoardAnswerDate").type("Date").description("답변날짜").optional(),
                                fieldWithPath("questionBoardAnswer").type(JsonFieldType.STRING).description("답변내용").optional(),
                                fieldWithPath("memberResponseDto.memberNumber").type(JsonFieldType.NUMBER).description("회원 고유 번호"),
                                fieldWithPath("memberResponseDto.memberNickname").type(JsonFieldType.STRING).description("회원 닉네임")
                        )

                ));
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("질문게시글 생성 실패 테스트")
    @Test
    public void createQuestionBoardTestShouldBeFail() throws Exception {
        Map<String, Object> questionBoardRequestDto = new HashMap<>();

        questionBoardRequestDto.put("questionBoardNumber", 1);
        questionBoardRequestDto.put("memberResponseDto", 1);
        questionBoardRequestDto.put("questionBoardTitle", "");
        questionBoardRequestDto.put("questionBoardContents", "contents");

        mockMvc.perform(post("/question")
                        .content(objectMapper.writeValueAsString(questionBoardRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("질문글 페이지 가져오기 테스트")
    @Test
    public void getQuestionPageTestShouldBeSuccess() throws Exception {
        Map<String, Object> requestPage = new HashMap<>();

        requestPage.put("page", 0);
        requestPage.put("size", 10);

        List<QuestionResponseDto> listDto = new ArrayList<>();
        listDto.add(questionResponseDto);
        listDto.add(questionResponseDto);

        doReturn(listDto).when(questionBoardService).requestPage(any());

        mockMvc.perform(get("/question/page")
                        .content(objectMapper.writeValueAsString(requestPage))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("question/page",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("page").description("페이지 번호"),
                                fieldWithPath("size").description("한페이지당 게시물 갯수")
                        ),
                        responseFields(
                                fieldWithPath("[].questionBoardNumber").type(JsonFieldType.NUMBER).description("고유번호"),
                                fieldWithPath("[].questionBoardTitle").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("[].questionBoardContents").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].questionBoardCreateDate").type("Date").description("작성날짜"),
                                fieldWithPath("[].questionBoardAnswerDate").type("Date").description("답변날짜").optional(),
                                fieldWithPath("[].questionBoardAnswer").type(JsonFieldType.STRING).description("답변내용").optional(),
                                fieldWithPath("[].memberResponseDto.memberNumber").type(JsonFieldType.NUMBER).description("회원 고유 번호"),
                                fieldWithPath("[].memberResponseDto.memberNickname").type(JsonFieldType.STRING).description("회원 닉네임")
                        )
                ));
    }
}
