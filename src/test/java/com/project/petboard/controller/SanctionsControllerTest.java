package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.SanctionsController;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Map<String, Object> sanctionsDto = new HashMap<>();

        sanctionsDto.put("sanctionsKey", "sanctions");
        sanctionsDto.put("sanctionsValue", 5);
        sanctionsDto.put("sanctionsContents", "contentsconconconcon");

        mockMvc.perform(post("/sanctions/admin")
                        .content(objectMapper.writeValueAsString(sanctionsDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
                    .andExpect(status().isOk());
        assertThat(sanctionsRepository.findBySanctionsKey(sanctionsKey)).isNull();
    }

    @WithMockUser(roles = "ADMIN")
    @DisplayName("제재종류 페이지 가져오기 테스트")
    @Test
    public void getSanctionsPageTestShouldBeSuccess() throws Exception {
        mockMvc.perform(get("/sanctions/page/admin")
                        .param("page",String.valueOf(0)))
                .andExpect(status().isOk());
    }
}
