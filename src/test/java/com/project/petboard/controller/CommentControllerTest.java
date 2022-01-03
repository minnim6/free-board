package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.CommentController;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.comment.CommentService;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

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

}
