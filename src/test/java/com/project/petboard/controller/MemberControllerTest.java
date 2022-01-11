package com.project.petboard.controller;

import com.project.petboard.appilcation.MemberController;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.MemberService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(value = MemberController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class MemberControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    Long memberNumber = 1L;

    @DisplayName("카카오 로그인 테스트")
    @Test
    public void KakaoLoginTestShouldBeSuccess() throws Exception {

        String code = "testCode";
        String accessToken = "testAccessToken";
        String refreshToken = "testRefreshToken";

        doReturn(new ResponseJwt(accessToken,refreshToken,new Date())).when(memberService).loginMember(code);

        mockMvc.perform(get("/kakao/login")
                .param("code",code))
                .andExpect(status().isOk())
                .andDo(document("member/login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("code").description("카카오 로그인 api시 자동으로 오는 code")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("액세스토큰"),
                                fieldWithPath("refreshToken").description("리프레쉬 토큰"),
                                fieldWithPath("accessTokenExpireTime").description("액세스 토큰 만료 시간")
                        )

                ));
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("회원 삭제 테스트")
    @Test
    public void deleteMemberTestShouldBeSuccess() throws Exception {
        mockMvc.perform(patch("/member")
                .param("memberNumber",String.valueOf(memberNumber)))
                .andExpect(status().isOk())
                .andDo(document("member/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberNumber").description("회원 번호")
                        )));
    }
}
