package com.project.petboard.integration;

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
public class MemberTest {

    @LocalServerPort
    int port;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    String token;

    @BeforeEach
    void setUp() {
        Date tokenExpireDate = new Date(new Date().getTime() + 100000L);
        token = jwtTokenUtil.crateAccessToken(1L,tokenExpireDate);
        RestAssured.port = port;
    }

    @DisplayName("회원 탈퇴 테스트")
    @Test
    public void deletePostTest(){

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("memberNumber",String.valueOf(1)).log().all()
                .when().patch("/member")
                .then().statusCode(HttpStatus.OK.value());
    }
}
