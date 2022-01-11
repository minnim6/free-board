package com.project.petboard.service;

import com.project.petboard.domain.member.*;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.kakao.KakaoUtil;
import com.project.petboard.infrastructure.kakao.RequestKakao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(member);
        //when
        memberService.deleteMember(1L);
        //then
        assertThat(memberRepository.findByMemberNumber(1L).getMemberStatus()).isEqualTo(MemberStatus.N);
    }
}
