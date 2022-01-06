package com.project.petboard.controller;

import com.project.petboard.appilcation.MemberController;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.MemberService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MemberController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class MemberControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    private final Long memberNumber = 1L;

    @DisplayName("카카오 로그인 테스트")
    @Test
    public void KakaoLoginTestShouldBeSuccess() throws Exception {

        String code = "testCode";

        doReturn(new ResponseJwt()).when(memberService).loginMember(code);

        mockMvc.perform(get("/kakao/login")
                .param("code",code))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("회원 삭제 테스트")
    @Test
    public void deleteMemberTestShouldBeSuccess() throws Exception {
        mockMvc.perform(patch("/member")
                .param("memberNumber",String.valueOf(memberNumber)))
                .andExpect(status().isOk());
    }
}
