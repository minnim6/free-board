package com.project.petboard.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.request.RecommentRequestTestDto;
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
public class RecommentTest {

    @LocalServerPort
    int port;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    ObjectMapper objectMapper;

    String token;

    @BeforeEach
    void setUp() {
        Date tokenExpireDate = new Date(new Date().getTime() + 100000L);
        token = jwtTokenUtil.crateAccessToken(1L,tokenExpireDate);
        RestAssured.port = port;
    }

    @DisplayName("대댓글 작성 테스트")
    @Test
    public void createRecommentTest() throws JsonProcessingException {

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(objectMapper.writeValueAsString(new RecommentRequestTestDto())).log().all()
                .when().post("/recomment")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("대댓글 페이지 가져오기 테스트")
    @Test
    public void requestPageTest(){

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("commentNumber",String.valueOf(2)).log().all()
                .when().get("/recomment/page")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("대댓글 삭제 테스트")
    @Test
    public void deleteRecommentTest(){

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("recommentNumber",String.valueOf(2)).log().all()
                .when().delete("/recomment")
                .then().statusCode(HttpStatus.OK.value());
    }

}
