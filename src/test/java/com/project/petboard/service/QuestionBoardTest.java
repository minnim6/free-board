package com.project.petboard.service;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.questionboard.*;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionBoardTest {

    MemberRepository memberRepository = mock(MemberRepository.class);

    QuestionBoardRepository questionBoardRepository = mock(QuestionBoardRepository.class);

    QuestionBoardService questionBoardService = new QuestionBoardService(questionBoardRepository, memberRepository);

    private QuestionBoard questionBoard;

    private QuestionResponseDto questionResponseDto;

    private QuestionBoardRequestDto questionBoardRequestDto;

    @Before
    public void setup() {
        questionBoardRequestDto = new QuestionBoardRequestDto(1L,
                1L, "title", "contents");
        questionBoard = questionBoardRequestDto.toEntity(new Member());
    }

    @DisplayName("질문게시글 생성 테스트")
    @Test
    public void createQuestionBoardShouldBeSuccess() {
        //given
        given(questionBoardRepository.save(any())).willReturn(questionBoard);
        //when
        Long postNumber = questionBoardService.createQuestionBoard(questionBoardRequestDto);
        //then
        assertThat(questionBoard.getQuestionBoardNumber()).isEqualTo(postNumber);
    }

    @DisplayName("질문게시글 생성 실패 테스트")
    @Test
    public void createQuestionBoardShouldBeFail() {
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(new Member());
        given(questionBoardRepository.save(any())).willReturn(null);
        try {
            //when
            Long postNumber = questionBoardService.createQuestionBoard(questionBoardRequestDto);
        } catch (CustomErrorException e) {
            //then
            assertThat(e.getErrorCode()).isEqualTo(HttpErrorCode.BAD_REQUEST);
        }
    }

    @DisplayName("질문게시글 조회 테스트")
    @Test
    public void findQuestionBoardShouldBeSuccess() {
        //given
        given(questionBoardRepository.save(any())).willReturn(questionBoard);
        given(questionBoardRepository.findByQuestionBoardNumber(1L)).willReturn(questionBoard);
        //when
        questionResponseDto = questionBoardService.fetchQuestionBoard(1L);
        //then
        assertThat(questionResponseDto).isNotNull();
    }


    @DisplayName("질문게시글 조회 실패 테스트")
    @Test
    public void findQuestionBoardShouldBeFail() {
        //given
        given(questionBoardRepository.save(any())).willReturn(questionBoard);
        given(questionBoardRepository.findByQuestionBoardNumber(1L)).willReturn(null);
        try {
            //when
            Long postNumber = questionBoardService.createQuestionBoard(questionBoardRequestDto);
        } catch (CustomErrorException e) {
            //then
            assertThat(e.getErrorCode()).isEqualTo(HttpErrorCode.NOT_FOUND);
        }
    }

    @DisplayName("질문게시글 삭제테스트")
    @Test
    public void deleteQuestionBoardShouldBeSuccess() {
        //when
        questionBoardService.deleteQuestionBoard(1L);
        //then
        verify(questionBoardRepository).deleteById(1L);
    }
}
