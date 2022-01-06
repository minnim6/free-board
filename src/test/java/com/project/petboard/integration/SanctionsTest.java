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
public class SanctionsTest {

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

    @DisplayName("제재종류 추가 테스트")
    @Test
    public void crateSanctionsTest(){
        Map<String, Object> sanctionsRequestDto = new HashMap<>();

        sanctionsRequestDto.put("sanctionsKey", "게시물");
        sanctionsRequestDto.put("sanctionsValue", 1);
        sanctionsRequestDto.put("sanctionsContents", "contentscontents");

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(sanctionsRequestDto).log().all()
                .when().post("/sanctions/admin")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("제재종류 페이지 가져오기 테스트")
    @Test
    public void getCSancionsPage(){
        Pageable pageable = PageRequest.of(1, 10);

        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .body(pageable).log().all()
                .when().get("/sanctions/page/admin")
                .then().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("제재종류 삭제 테스트")
    @Test
    public void deleteSanctionsTest(){
        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .param("sanctionsKey","게시물게시물").log().all()
                .when().delete("/sanctions/admin")
                .then().statusCode(HttpStatus.OK.value());
    }
}


