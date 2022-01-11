package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.JwtController;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(value = JwtController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class JwtControllerTest{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    private final Long memberNumber = 1L;

    @DisplayName("jwt 재발급 요청 테스트")
    @Test
    public void requestTokenTestShouldBeSuccess() throws Exception {

        String accessToken = "testAccessToken";
        String refreshToken = "testRefreshToken";

        doReturn(new ResponseJwt(accessToken,refreshToken,new Date())).when(jwtTokenUtil).requestToken(accessToken,refreshToken);

        mockMvc.perform(post("/jwt")
                .header("access_token",accessToken)
                .header("refresh_token",refreshToken))
                .andExpect(status().isOk())
                .andDo(document("jwt/requestToken",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("access_token").description("액세스 토큰"),
                                headerWithName("refresh_token").description("리프레쉬 토큰")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("새로운 액세스토큰"),
                                fieldWithPath("refreshToken").description("기존 리프레쉬 토큰 재전달"),
                                fieldWithPath("accessTokenExpireTime").description("새로운 액세스 토큰 만료 시간")
                                )
                        ))
                .andExpect(jsonPath("$.accessToken").value(accessToken))
                .andExpect(jsonPath("$.refreshToken").value(refreshToken));
    }
}
