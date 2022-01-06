package com.project.petboard.service;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostResponseDto;
import com.project.petboard.domain.questionboard.*;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionBoardServiceTest {

    MemberRepository memberRepository = mock(MemberRepository.class);

    QuestionBoardRepository questionBoardRepository = mock(QuestionBoardRepository.class);

    QuestionBoardService questionBoardService = new QuestionBoardService(questionBoardRepository, memberRepository);

    private QuestionBoard questionBoard;

    private QuestionBoardRequestDto questionBoardRequestDto;

    @BeforeEach
    public void setup() {
        questionBoard = QuestionBoard.builder()
                .member(new Member())
                .questionBoardTitle("title")
                .questionBoardContents("contents")
                .build();
        questionBoardRequestDto = new QuestionBoardRequestDto();
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

    @DisplayName("질문게시글 답변 테스트")
    @Test
    public void questionBoardAnswerShouldBeSuccess(){
        given(questionBoardRepository.findByQuestionBoardNumber(1L)).willReturn(questionBoard);

        questionBoardService.questionBoardAnswer(new QuestionBoardAnswerRequestDto(1L,"answer"));

        assertThat(questionBoardRepository.findByQuestionBoardNumber(1L).getQuestionBoardAnswer()).isEqualTo("answer");
    }

    @DisplayName("질문게시글 조회 테스트")
    @Test
    public void findQuestionBoardShouldBeSuccess() {
        //given
        given(questionBoardRepository.save(any())).willReturn(questionBoard);
        given(questionBoardRepository.findByQuestionBoardNumber(1L)).willReturn(questionBoard);
        //when
        QuestionResponseDto questionResponseDto = questionBoardService.fetchQuestionBoard(1L);
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

    @DisplayName("페이지 가져오기 테스트")
    @Test
    public void getQuestionBoardPageTestShouldBeSuccess() {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        List<QuestionBoard> questionBoardList = new ArrayList<>();
        questionBoardList.add(questionBoard);
        questionBoardList.add(questionBoard);
        Page<QuestionBoard> questionBoards = new PageImpl<>(questionBoardList);
        given(questionBoardRepository.findAll(pageable)).willReturn(questionBoards);
        //when
        Page<QuestionResponseDto> questionResponseDtos = questionBoardService.requestPage(pageable);
        //then
        assertThat(questionResponseDtos.getSize()).isEqualTo(2);
    }
}
