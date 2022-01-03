package com.project.petboard.service;

import com.project.petboard.domain.member.*;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import com.project.petboard.infrastructure.kakao.KakaoUtil;
import com.project.petboard.infrastructure.kakao.RequestKakao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class MemberServiceTest {

    MemberRepository memberRepository = mock(MemberRepository.class);

    RoleRepository roleRepository = mock(RoleRepository.class);

    KakaoUtil kakaoUtil = mock(KakaoUtil.class);

    JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);

    MemberService memberService = new MemberService(memberRepository,roleRepository,kakaoUtil,jwtTokenUtil);

    private RequestKakao requestKakao;

    private Member member;

    @BeforeEach
    public void setup(){
        member = Member.builder()
                .memberNickname("nickname")
                .memberSnsId("1")
                .memberEmail("test@email.com")
                .memberSignupCategory(MemberSignupCategory.KAKAO)
                .build();
        requestKakao = new RequestKakao();
    }

    @DisplayName("멤버 삭제 테스트")
    @Test
    public void deleteMemberTestShouldSuccess(){
        //when
        memberRepository.deleteById(1L);
        //then
        verify(memberRepository).deleteById(1L);
    }
}
