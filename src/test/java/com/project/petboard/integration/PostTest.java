package com.project.petboard.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.request.PostCreateRequestTestDto;
import com.project.petboard.request.PostUpdateRequestTestDto;
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
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostTest {

    @LocalServerPort
    int port;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    String token;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        Date tokenExpireDate = new Date(new Date().getTime() + 1000000L);
        token = jwtTokenUtil.crateAccessToken(1L,tokenExpireDate);
        RestAssured.port = port;
    }


    @DisplayName("게시물 작성 테스트")
    @Test
    public void createPostTest() throws Exception {
        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(objectMapper.writeValueAsString(new PostCreateRequestTestDto())).log().all()
                .when().post("/post")
                .then().statusCode(HttpStatus.OK.value());

    }

    @DisplayName("게시물 페이지 가져오기 테스트")
    @Test
    public void requestPageTest(){

        Map<String, Object> requestPage = new HashMap<>();

        requestPage.put("size", 10);
        requestPage.put("page", 0);

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(requestPage).log().all()
                .when().get("/post/page")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("게시물 삭제 테스트")
    @Test
    public void deletePostTest(){

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("postNumber",String.valueOf(1)).log().all()
                .when().delete("/post")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("게시물 상태변경 테스트")
    @Test
    public void visibleChangePostStatus(){

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("postNumber",String.valueOf(2)).log().all()
                .when().patch("/post/status")
                .then().statusCode(HttpStatus.OK.value());
    }


    @DisplayName("게시물 업데이트 테스트")
    @Test
    public void updatePostTest() throws JsonProcessingException {

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(objectMapper.writeValueAsString(new PostUpdateRequestTestDto())).log().all()
                .when().patch("/post")
                .then().statusCode(HttpStatus.OK.value());
    }
}
