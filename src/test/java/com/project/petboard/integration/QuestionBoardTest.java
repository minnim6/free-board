package com.project.petboard.integration;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
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
public class QuestionBoardTest {

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

    @DisplayName("질문 게시글 가져오기 테스트")
    @Test
    public void fetchQuestionBoardTest(){
        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("questionNumber",String.valueOf(1)).log().all()
                .when().get("/question")
                .then().statusCode(HttpStatus.OK.value());
    }

    @Order(5)
    @DisplayName("질문 게시글 삭제 테스트")
    @Test
    public void deleteQuestionBoardTest(){
        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("questionNumber",String.valueOf(2)).log().all()
                .when().delete("/question")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("질문 게시글 답변 테스트")
    @Test
    public void questionBoardAnswer(){
        Map<String, Object>  answerRequestDto = new HashMap<>();

        answerRequestDto.put("questionBoardNumber", 1L);
        answerRequestDto.put("questionBoardContents", "contentscontentscontentscontents");

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(answerRequestDto).log().all()
                .when().patch("/question/answer")
                .then().statusCode(HttpStatus.OK.value());

    }

    @DisplayName("질문 게시글 생성 테스트")
    @Test
    public void createQuestionBoardTest(){
        Map<String, Object>  questionBoardRequestDto = new HashMap<>();

        questionBoardRequestDto.put("memberNumber", 1);
        questionBoardRequestDto.put("questionBoardTitle", "title");
        questionBoardRequestDto.put("questionBoardContents", "contentscontentscontentscontents");

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(questionBoardRequestDto).log().all()
                .when().post("/question")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("질문 게시글 페이지 가져오기 테스트")
    @Test
    public void requestQuestionBoardPage(){
        Pageable pageable = PageRequest.of(1, 10);

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(pageable).log().all()
                .when().get("/question/page")
                .then().statusCode(HttpStatus.OK.value());
    }
}
