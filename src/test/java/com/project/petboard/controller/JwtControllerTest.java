package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.JwtController;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.doReturn;

import java.util.Date;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JwtController.class)
public class JwtControllerTest {

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

        doReturn(new ResponseJwt(accessToken,refreshToken,new Date().getTime())).when(jwtTokenUtil).requestToken(accessToken,refreshToken);

        mockMvc.perform(post("/jwt")
                .header("access_token",accessToken)
                .header("refresh_token",refreshToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(accessToken))
                .andExpect(jsonPath("$.refreshToken").value(refreshToken));
    }
}
