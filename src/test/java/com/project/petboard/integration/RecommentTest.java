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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecommentTest {

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

    @DisplayName("대댓글 작성 테스트")
    @Test
    public void createRecommentTest(){
        Map<String, Object> recommentRequestDto = new HashMap<>();

        recommentRequestDto.put("memberNumber", 1);
        recommentRequestDto.put("postNumber", 1);
        recommentRequestDto.put("commentNumber", 1);
        recommentRequestDto.put("recommentContents", "contents");

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(recommentRequestDto).log().all()
                .when().post("/recomment")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("대댓글 페이지 가져오기 테스트")
    @Test
    public void requestPageTest(){

        Pageable pageable = PageRequest.of(1, 10);

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(pageable).log().all()
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
