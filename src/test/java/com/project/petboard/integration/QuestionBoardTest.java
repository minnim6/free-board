package com.project.petboard.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.request.QuestionAnswerRequestTestDto;
import com.project.petboard.request.QuestionRequestTestDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
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
public class QuestionBoardTest {


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
        token = jwtTokenUtil.crateAccessToken(1L, tokenExpireDate);
        RestAssured.port = port;
    }

    @DisplayName("질문 게시글 가져오기 테스트")
    @Test
    public void fetchQuestionBoardTest() {
        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .param("questionNumber", String.valueOf(1)).log().all()
                .when().get("/question")
                .then().statusCode(HttpStatus.OK.value());
    }

    @Order(5)
    @DisplayName("질문 게시글 삭제 테스트")
    @Test
    public void deleteQuestionBoardTest() {
        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .param("questionNumber", String.valueOf(2)).log().all()
                .when().delete("/question")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("질문 게시글 답변 테스트")
    @Test
    public void questionBoardAnswer() throws JsonProcessingException {

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(objectMapper.writeValueAsString(new QuestionAnswerRequestTestDto())).log().all()
                .when().patch("/question/answer")
                .then().statusCode(HttpStatus.OK.value());

    }

    @DisplayName("질문 게시글 생성 테스트")
    @Test
    public void createQuestionBoardTest() throws JsonProcessingException {

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(objectMapper.writeValueAsString(new QuestionRequestTestDto())).log().all()
                .when().post("/question")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("질문 게시글 페이지 가져오기 테스트")
    @Test
    public void requestQuestionBoardPage() throws JsonProcessingException {

        Map<String, Object> requestPage = new HashMap<>();

        requestPage.put("size", 10);
        requestPage.put("page", 0);

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(requestPage).log().all()
                .when().get("/question/page")
                .then().statusCode(HttpStatus.OK.value());
    }
}

