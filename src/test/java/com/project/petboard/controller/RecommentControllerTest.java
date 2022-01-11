package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.RecommentController;
import com.project.petboard.domain.member.MemberResponseDto;
import com.project.petboard.domain.recomment.RecommentRepository;
import com.project.petboard.domain.recomment.RecommentResponseDto;
import com.project.petboard.domain.recomment.RecommentService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.request.RecommentRequestTestDto;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@WebMvcTest(value = RecommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class RecommentControllerTest{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommentRepository recommentRepository;

    @MockBean
    private RecommentService recommentService;

    private final Long recommentNumber = 1L;

    private RecommentResponseDto recommentResponseDto;

    @BeforeEach
    public void setup(){
        MemberResponseDto memberDto = new MemberResponseDto(1L, "nickname");
        recommentResponseDto = new RecommentResponseDto(1L,memberDto,"내요오오옹",new Date());
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("대댓글 작성 테스트")
    @Test
    public void createRecommentTestShouldBeSuccess() throws Exception {
        mockMvc.perform(post("/recomment")
                        .content(objectMapper.writeValueAsString(new RecommentRequestTestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(document("recomment/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("memberNumber").type(JsonFieldType.NUMBER).description("회원 번호"),
                                fieldWithPath("postNumber").type(JsonFieldType.NUMBER).description("게시물 번호"),
                                fieldWithPath("commentNumber").type(JsonFieldType.NUMBER).description("댓글 번호"),
                                fieldWithPath("recommentContents").type(JsonFieldType.STRING).description("댓글 내용")
                        )));
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("대댓글 작성 실패 테스트")
    @Test
    public void createRecommentTestShouldBeFail() throws Exception {
        mockMvc.perform(post("/recomment")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("대댓글 삭제 테스트")
    @Test
    public void deleteRecommentTestShouldBeSuccess() throws Exception {
        mockMvc.perform(delete("/recomment")
                .param("recommentNumber",String.valueOf(recommentNumber)))
                .andExpect(status().isOk())
                .andDo(document("recomment/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("recommentNumber").description("댓글 번호")
                        )));
        assertThat(recommentRepository.findByRecommentNumber(recommentNumber)).isNull();
    }

    @DisplayName("대댓글 페이지 가져오기 테스트")
    @Test
    public void getRecommentPageTestShouldBeSuccess() throws Exception {

        List<RecommentResponseDto> listDto = new ArrayList<>();
        listDto.add(recommentResponseDto);
        listDto.add(recommentResponseDto);
        doReturn(listDto).when(recommentService).requestRecommentPage(any());

       mockMvc.perform(get("/recomment/page")
               .param("commentNumber",String.valueOf(1)))
               .andExpect(status().isOk())
               .andDo(document("recomment/page",
                       preprocessRequest(prettyPrint()),
                       preprocessResponse(prettyPrint()),
                       requestParameters(
                               parameterWithName("commentNumber").description("가져올 대댓글들의 상단 댓글 번호")
                       ),
                       responseFields(
                               fieldWithPath("[].recommentNumber").description("댓글 고유번호"),
                               fieldWithPath("[].recommentContents").description("내용"),
                               fieldWithPath("[].recommentCreateDate").description("작성날짜"),
                               fieldWithPath("[].memberResponseDto.memberNumber").description("회원 고유 번호"),
                               fieldWithPath("[].memberResponseDto.memberNickname").description("회원 닉네임")
                       )
                       ));
    }

}
