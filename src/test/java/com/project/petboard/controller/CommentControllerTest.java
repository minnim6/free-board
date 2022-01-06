package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.CommentController;
import com.project.petboard.appilcation.SanctionsController;
import com.project.petboard.controller.SecurityConfig.TestSecurityConfig;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.comment.CommentService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.infrastructure.jwt.JwtFilter;
import com.project.petboard.infrastructure.jwt.JwtSecurityConfig;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(value = CommentController.class,excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class})})
public class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private CommentService commentService;


    @WithMockUser(roles = "MEMBER")
    @DisplayName("댓글 생성 테스트")
    @Test
    public void createCommentTestShouldBeSuccess() throws Exception {

        Map<String, Object> commentRequestDto = new HashMap<>();

        commentRequestDto.put("postNumber", 1);
        commentRequestDto.put("memberNumber", 1);
        commentRequestDto.put("commentContents", "contents");

        mockMvc.perform(post("/comment")
                        .content(objectMapper.writeValueAsString(commentRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("댓글 생성실패 테스트")
    @Test
    public void createCommentTestShouldBeFail() throws Exception {
        Map<String, Object> commentRequestDto = new HashMap<>();

        commentRequestDto.put("postNumber", 1);
        commentRequestDto.put("memberNumber", 1);
        commentRequestDto.put("commentContents", "");

        mockMvc.perform(post("/comment")
                        .content(objectMapper.writeValueAsString(commentRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
