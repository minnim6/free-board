package com.project.petboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.petboard.appilcation.CommentController;
import com.project.petboard.appilcation.QuestionBoardController;
import com.project.petboard.appilcation.RecommentController;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentResponseDto;
import com.project.petboard.domain.comment.CommentService;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.recomment.RecommentRepository;
import com.project.petboard.domain.recomment.RecommentService;
import com.project.petboard.infrastructure.configure.SecurityConfig;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
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

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(value = RecommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class RecommentControllerTest{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommentRepository recommentRepository;

    @MockBean
    private RecommentService recommentService;

    private final Long recommentNumber = 1L;

    @WithMockUser(roles = "MEMBER")
    @DisplayName("대댓글 작성 테스트")
    @Test
    public void createRecommentTestShouldBeSuccess() throws Exception {
        Map<String, Object> recommentRequestDto = new HashMap<>();

        recommentRequestDto.put("recommentContents","contents");
        recommentRequestDto.put("commentNumber",1);
        recommentRequestDto.put("memberNumber", 1);
        recommentRequestDto.put("postNumber", 1);

        mockMvc.perform(post("/recomment")
                        .content(objectMapper.writeValueAsString(recommentRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("대댓글 작성 실패 테스트")
    @Test
    public void createRecommentTestShouldBeFail() throws Exception {
        Map<String, Object> recommentRequestDto = new HashMap<>();

        recommentRequestDto.put("recommentContents","");
        recommentRequestDto.put("commentNumber",1);
        recommentRequestDto.put("memberNumber", 1);
        recommentRequestDto.put("postNumber", 1);

        mockMvc.perform(post("/recomment")
                        .content(objectMapper.writeValueAsString(recommentRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(roles = "MEMBER")
    @DisplayName("대댓글 삭제 테스트")
    @Test
    public void deleteRecommentTestShouldBeSuccess() throws Exception {
        mockMvc.perform(delete("/recomment")
                .param("recommentNumber",String.valueOf(recommentNumber)))
                .andExpect(status().isOk());
        assertThat(recommentRepository.findByRecommentNumber(recommentNumber)).isNull();
    }

}
