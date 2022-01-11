package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.SanctionsController;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.request.SanctionsRequestTestDto;
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
@WebMvcTest(value = SanctionsController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class) })
public class SanctionsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SanctionsRepository sanctionsRepository;

    @MockBean
    private SanctionsService sanctionsService;

    private final String sanctionsKey = "sanctionKey";

    @WithMockUser(roles = "ADMIN")
    @DisplayName("제재 종류 생성 테스트")
    @Test
    public void createSanctionsTestShouldBeSuccess() throws Exception {

        mockMvc.perform(post("/sanctions/admin")
                        .content(objectMapper.writeValueAsString(new SanctionsRequestTestDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("sanctions/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("sanctionsKey").type(JsonFieldType.STRING).description("제재종류 제목"),
                                fieldWithPath("sanctionsValue").type(JsonFieldType.NUMBER).description("기준 넘버"),
                                fieldWithPath("sanctionsContents").type(JsonFieldType.STRING).description("내용")
                        )
                ));
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("제재 종류 생성 실패 테스트")
    @Test
    public void createSanctionsTestShouldBeFail() throws Exception {
        Map<String, Object> sanctionsDto = new HashMap<>();

        sanctionsDto.put("sanctionsKey", "");
        sanctionsDto.put("sanctionsValue", 5);
        sanctionsDto.put("sanctionsContents", "contents");

        mockMvc.perform(post("/sanctions/admin")
                        .content(objectMapper.writeValueAsString(sanctionsDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("제재 종류 삭제 테스트")
    @Test
    public void deleteSanctionsTestShouldBeSuccess() throws Exception {
            mockMvc.perform(delete("/sanctions/admin")
                            .param("sanctionsKey",sanctionsKey))
                    .andExpect(status().isOk())
                    .andDo(document("sanctions/delete",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestParameters(
                                    parameterWithName("sanctionsKey").description("제재종류 제목")
                            )));
        assertThat(sanctionsRepository.findBySanctionsKey(sanctionsKey)).isNull();
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("제재종류 페이지 가져오기 테스트")
    @Test
    public void getSanctionsPageTestShouldBeSuccess() throws Exception {
        Map<String, Object> requestPage = new HashMap<>();

        requestPage.put("page", 0);
        requestPage.put("size", 10);

        List<SanctionsRequestTestDto> listDto = new ArrayList<>();
        listDto.add(new SanctionsRequestTestDto());
        listDto.add(new SanctionsRequestTestDto());

        doReturn(listDto).when(sanctionsService).fetchSanctionsList(any());
        mockMvc.perform(get("/sanctions/page/admin")
                .content(objectMapper.writeValueAsString(requestPage))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("sanctions/page",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("page").description("페이지 번호"),
                                fieldWithPath("size").description("한페이지당 게시물 갯수")
                        ),
                        responseFields(
                                fieldWithPath("[].sanctionsValue").description("제재종류 제목"),
                                fieldWithPath("[].sanctionsKey").description("기준 넘버"),
                                fieldWithPath("[].sanctionsContents").description("내용")
                                )
                ));
    }
}
