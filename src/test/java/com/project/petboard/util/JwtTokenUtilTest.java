package com.project.petboard.util;

import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class JwtTokenUtilTest {

    private final String secretKey = "testKey";

    MemberRepository memberRepository = mock(MemberRepository.class);
    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(secretKey, memberRepository);

    @Test
    public void 토큰재요청_성공() {

        long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
        long nowDate = new Date().getTime();

        String accessToken = jwtTokenUtil.crateAccessToken(1L, jwtTokenUtil.crateTokenExpireDate(nowDate, ACCESS_TOKEN_EXPIRE_TIME));
        String refreshToken = jwtTokenUtil.createRefreshToken(jwtTokenUtil.crateTokenExpireDate(nowDate, ACCESS_TOKEN_EXPIRE_TIME));
        given(memberRepository.existsByMemberRefreshToken(refreshToken)).willReturn(true);

        assertThat(jwtTokenUtil.requestToken(accessToken, refreshToken)).isInstanceOf(ResponseJwt.class);

        verify(memberRepository, times(1)).existsByMemberRefreshToken(refreshToken); // existsByMemberRefreshToken 1회 실행했냐?
    }

    @Test
    public void 토큰재요청_실패(){

        long ACCESS_TOKEN_EXPIRE_TIME = 0;
        long nowDate = new Date().getTime();

        String accessToken = jwtTokenUtil.crateAccessToken(1L, jwtTokenUtil.crateTokenExpireDate(nowDate, ACCESS_TOKEN_EXPIRE_TIME));
        String refreshToken = jwtTokenUtil.createRefreshToken(jwtTokenUtil.crateTokenExpireDate(nowDate, ACCESS_TOKEN_EXPIRE_TIME));

        try {
            given(memberRepository.existsByMemberRefreshToken(refreshToken)).willReturn(true);
        }catch (CustomErrorException e){
            assertThat(e.getErrorCode().getMessage()).isEqualTo("TOKEN_EXPIRE");
        }
    }

    @Test
    public void 토큰유효기간_유효(){

        long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
        long nowDate = new Date().getTime();

        String accessToken = jwtTokenUtil.crateAccessToken(1L, jwtTokenUtil.crateTokenExpireDate(nowDate, ACCESS_TOKEN_EXPIRE_TIME));

        assertThat(jwtTokenUtil.isValidateToken(accessToken)).isEqualTo(true);
    }

    @Test
    public void 토큰유효기간_만료(){

        long ACCESS_TOKEN_EXPIRE_TIME = 0;
        long nowDate = new Date().getTime();

        String accessToken = jwtTokenUtil.crateAccessToken(1L, jwtTokenUtil.crateTokenExpireDate(nowDate, ACCESS_TOKEN_EXPIRE_TIME));

        assertThat(jwtTokenUtil.isValidateToken(accessToken)).isEqualTo(false);
    }
}
