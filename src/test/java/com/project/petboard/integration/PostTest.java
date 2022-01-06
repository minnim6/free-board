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
public class PostTest {

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


    @DisplayName("게시물 작성 테스트")
    @Test
    public void createPostTest() {

        Map<String, Object> postRequestDto = new HashMap<>();

        postRequestDto.put("memberNumber", 1);
        postRequestDto.put("postTitle", "title");
        postRequestDto.put("postContents", "contents");
        postRequestDto.put("postCategory", "category");

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(postRequestDto).log().all()
                .when().post("/post")
                .then().statusCode(HttpStatus.OK.value());

    }

    @DisplayName("게시물 페이지 가져오기 테스트")
    @Test
    public void requestPageTest(){

        Pageable pageable = PageRequest.of(1, 10);

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(pageable).log().all()
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
    public void updatePostTest(){

        Map<String, Object> postRequestDto = new HashMap<>();

        postRequestDto.put("memberNumber", 1);
        postRequestDto.put("postTitle", "title2");
        postRequestDto.put("postContents", "contents");
        postRequestDto.put("postCategory", "category");

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(postRequestDto).log().all()
                .when().patch("/post")
                .then().statusCode(HttpStatus.OK.value());
    }
}
