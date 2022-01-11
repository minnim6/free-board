package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.page.RequestPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionBoardService {

    private final QuestionBoardRepository questionBoardRepository;

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public QuestionResponseDto fetchQuestionBoard(Long questionNumber) {
            return new QuestionResponseDto(questionBoardRepository.findByQuestionBoardNumber(questionNumber));
    }

    public void deleteQuestionBoard(Long questionNumber) {
        questionBoardRepository.deleteById(questionNumber);
    }

    public QuestionResponseDto createQuestionBoard(QuestionBoardRequestDto questionBoardDto) {
            return new QuestionResponseDto(questionBoardRepository.save(questionBoardDto.toEntity(getMemberEntity(questionBoardDto.getMemberNumber()))));
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDto> requestPage(RequestPage requestPage) {
            Pageable pageable = PageRequest.of(requestPage.getPage(),requestPage.getSize());
            List<QuestionResponseDto> page = questionBoardRepository.findAll(pageable).getContent().stream()
                    .map(questionBoard -> new QuestionResponseDto(questionBoard))
                    .collect(Collectors.toList());
            return page;
    }

    public void questionBoardAnswer(QuestionBoardAnswerRequestDto answerRequestDto){
       QuestionBoard questionBoard = questionBoardRepository.findByQuestionBoardNumber(answerRequestDto.getQuestionBoardNumber());
       questionBoard.completeAnswer(answerRequestDto);
    }

    public Member getMemberEntity(Long memberNumber){
        return memberRepository.findByMemberNumber(memberNumber);
    }
}
