package com.project.petboard.service;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.page.RequestPage;
import com.project.petboard.domain.questionboard.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        QuestionResponseDto questionBoard = questionBoardService.createQuestionBoard(questionBoardRequestDto);
        //then
        assertThat(questionBoard).isNotNull();
    }

    @DisplayName("질문게시글 생성 실패 테스트")
    @Test
    public void createQuestionBoardShouldBeFail() {
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(new Member());
        given(questionBoardRepository.save(any())).willReturn(null);
        try {
            //when
            QuestionResponseDto questionBoard = questionBoardService.createQuestionBoard(questionBoardRequestDto);
        } catch (NullPointerException e) {
            //then
            assertThat(questionBoardRepository.findByQuestionBoardNumber(1L)).isNull();
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
            QuestionResponseDto questionResponseDto = questionBoardService.createQuestionBoard(questionBoardRequestDto);
        } catch (NullPointerException e) {
            //then
            assertThat(questionBoardRepository.findByQuestionBoardNumber(1L)).isNull();
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
        Pageable pageable = PageRequest.of(0, 10);
        List<QuestionBoard> questionBoardList = new ArrayList<>();
        questionBoardList.add(questionBoard);
        questionBoardList.add(questionBoard);
        Page<QuestionBoard> questionBoards = new PageImpl<>(questionBoardList);
        given(questionBoardRepository.findAll(pageable)).willReturn(questionBoards);
        RequestPage requestPage = new RequestPage(0,10);
        //when
        List<QuestionResponseDto> questionResponseDtos = questionBoardService.requestPage(requestPage);
        //then
        assertThat(questionResponseDtos.size()).isEqualTo(2);
    }
}
