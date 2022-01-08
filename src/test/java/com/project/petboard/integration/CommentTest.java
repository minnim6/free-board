package com.project.petboard.integration;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
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

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentTest {

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

    @DisplayName("댓글 작성 테스트")
    @Test
    public void createCommentTest(){
        Map<String, Object> commentRequestDto = new HashMap<>();

        commentRequestDto.put("memberNumber", 1);
        commentRequestDto.put("postNumber", 1);
        commentRequestDto.put("commentContents", "contents");

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(commentRequestDto).log().all()
                .when().post("/comment")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("댓글 페이지 가져오기 테스트")
    @Test
    public void requestPageTest(){

        Pageable pageable = PageRequest.of(1, 10);

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(pageable).log().all()
                .when().get("/comment/page")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    public void deleteCommentTest(){

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("commentNumber",String.valueOf(2)).log().all()
                .when().delete("/comment")
                .then().statusCode(HttpStatus.OK.value());
    }
}
