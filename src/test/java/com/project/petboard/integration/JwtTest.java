package com.project.petboard.integration;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Date;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtTest {

    @LocalServerPort
    int port;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    MemberRepository memberRepository;

    String accessToken;

    String refreshToken;

    @BeforeEach
    void setUp() {
        Date tokenExpireDate = new Date(new Date().getTime()+10000);
        refreshToken =  jwtTokenUtil.createRefreshToken(new Date(tokenExpireDate.getTime()+100000));
        accessToken = jwtTokenUtil.crateAccessToken(1L,tokenExpireDate);
        Member member = memberRepository.findByMemberNumber(1L);
        member.setMemberRefreshToke(refreshToken);
        memberRepository.save(member);
        RestAssured.port = port;
    }

    @DisplayName("토큰 요청 테스트")
    @Test
    public void requestTokenTest(){
        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("access_token",accessToken)
                .header("refresh_token",refreshToken)
                .log().all()
                .when().post("/jwt")
                .then().statusCode(HttpStatus.OK.value());
    }

}
