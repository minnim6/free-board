package com.project.petboard.domain.questionboard;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostResponseDto;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionBoardService {

    private final QuestionBoardRepository questionBoardRepository;

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public QuestionResponseDto fetchQuestionBoard(Long questionNumber) {
        try {
            return new QuestionResponseDto(questionBoardRepository.findByQuestionBoardNumber(questionNumber));
        } catch (Exception e) {
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.NOT_FOUND);
        }
    }

    public void deleteQuestionBoard(Long questionNumber) {
        questionBoardRepository.deleteById(questionNumber);
    }

    public Long createQuestionBoard(QuestionBoardRequestDto questionBoardDto) {
        try {
            return questionBoardRepository.save(questionBoardDto.toEntity(getMemberEntity(questionBoardDto.getMemberNumber()))).getQuestionBoardNumber();
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(),HttpErrorCode.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponseDto> requestPage(Pageable pageable) {
        try {
            return questionBoardRepository.findAll(pageable).map(new Function<QuestionBoard, QuestionResponseDto>() {
                @Override
                public QuestionResponseDto apply(QuestionBoard questionBoard) {
                    // Conversion logic
                    return new QuestionResponseDto(questionBoard);
                }});
        } catch (Exception e) {
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.NOT_FOUND);
        }
    }

    public void questionBoardAnswer(QuestionBoardAnswerRequestDto answerRequestDto){
       QuestionBoard questionBoard = questionBoardRepository.findByQuestionBoardNumber(answerRequestDto.getQuestionBoardNumber());
       questionBoard.completeAnswer(answerRequestDto);
    }

    public Member getMemberEntity(Long memberNumber){
        return memberRepository.findByMemberNumber(memberNumber);
    }
}
