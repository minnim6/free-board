package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.SanctionsController;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(SanctionsController.class)
public class SanctionsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SanctionsRepository sanctionsRepository;

    @MockBean
    private SanctionsService sanctionsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    private final String sanctionsKey = "sanctionKey";

    @DisplayName("제재 종류 생성 테스트")
    @Test
    public void createSanctionsTestShouldBeSuccess() throws Exception {
        Map<String, Object> sanctionsDto = new HashMap<>();

        sanctionsDto.put("sanctionsKey", "key");
        sanctionsDto.put("sanctionsValue", 5);
        sanctionsDto.put("sanctionsContents", "contents");

        mockMvc.perform(post("/sanctions")
                        .content(objectMapper.writeValueAsString(sanctionsDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("제재 종류 삭제 테스트")
    @Test
    public void deleteSanctionsTestShouldBeSuccess() throws Exception {
            mockMvc.perform(delete("/sanctions")
                            .param("recommentNumber",String.valueOf(sanctionsKey)))
                    .andExpect(status().isOk());
        assertThat(sanctionsRepository.findBySanctionsKey(sanctionsKey)).isNull();
    }
}
