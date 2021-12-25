package com.project.petboard.service;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.MemberService;
import com.project.petboard.domain.member.RoleRepository;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import com.project.petboard.infrastructure.kakao.KakaoAccount;
import com.project.petboard.infrastructure.kakao.KakaoProfile;
import com.project.petboard.infrastructure.kakao.KakaoUtil;
import com.project.petboard.infrastructure.kakao.RequestKakao;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MemberServiceTest {

    MemberRepository memberRepository = mock(MemberRepository.class);
    RoleRepository roleRepository = mock(RoleRepository.class);
    KakaoUtil kakaoUtil = mock(KakaoUtil.class);
    JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);

    MemberService memberService = new MemberService(memberRepository, roleRepository, kakaoUtil, jwtTokenUtil);

    @Test
    public void test() {

    }

}
