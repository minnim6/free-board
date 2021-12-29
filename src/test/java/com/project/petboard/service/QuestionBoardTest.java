package com.project.petboard.service;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.questionboard.QuestionBoard;
import com.project.petboard.domain.questionboard.QuestionBoardDto;
import com.project.petboard.domain.questionboard.QuestionBoardRepository;
import com.project.petboard.domain.questionboard.QuestionBoardService;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionBoardTest {

    QuestionBoardRepository questionBoardRepository = mock(QuestionBoardRepository.class);

    QuestionBoardService questionBoardService = new QuestionBoardService(questionBoardRepository);

    private QuestionBoard questionBoard;

    private QuestionBoardDto questionBoardDto;

    @Before
    public void setup(){
         questionBoard = QuestionBoard.builder()
                .questionBoardTitle("testTitle")
                .questionBoardContents("testContents")
                .member(new Member())
                .build();
        questionBoardDto = new QuestionBoardDto(questionBoard);
    }

    @DisplayName("질문게시글 생성 테스트")
    @Test
    public void createQuestionBoardShouldBeSuccess(){
        //given
        given(questionBoardRepository.save(any())).willReturn(questionBoard);
        given(questionBoardRepository.findByQuestionBoardNumber(1L)).willReturn(questionBoard);
        //when
        Long postNumber = questionBoardService.createQuestionBoard(questionBoardDto);
        //then
        assertThat(questionBoard.getQuestionBoardNumber()).isEqualTo(postNumber);
    }

    @DisplayName("질문게시글 조회 실패 테스트")
    @Test
    public void findQuestionBoardShouldBeFail(){
        //given
        given(questionBoardRepository.save(any())).willReturn(questionBoard);
        given(questionBoardRepository.findByQuestionBoardNumber(1L)).willReturn(null);
        try {
            //when
            Long postNumber = questionBoardService.createQuestionBoard(questionBoardDto);
        }catch (CustomErrorException e){
            //then
            assertThat(e.getErrorCode()).isEqualTo(HttpErrorCode.NOT_FOUND);
        }
    }

    @DisplayName("질문게시글 삭제테스트")
    @Test
    public void deleteQuestionBoardShouldBeSuccess(){
        //when
        questionBoardService.deleteQuestionBoard(1L);
        //then
        verify(questionBoardRepository).deleteById(1L);
    }
}
