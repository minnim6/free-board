package com.project.petboard.integration;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;

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

    }

    @DisplayName("질문 게시글 삭제 테스트")
    @Test
    public void deleteQuestionBoardTest(){

    }

    @DisplayName("질문 게시글 답변 테스트")
    @Test
    public void questionBoardAnswer(){

    }

    @DisplayName("질문 게시글 생성 테스트")
    @Test
    public void createQuestionBoardTest(){

    }

    @DisplayName("질문 게시글 페이지 가져오기 테스트")
    @Test
    public void requestQuestionBoardPage(){

    }
}
