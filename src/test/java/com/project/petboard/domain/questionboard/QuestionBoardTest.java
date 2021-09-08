package com.project.petboard.domain.questionboard;


import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.dummy.MemberDummy;
import com.project.petboard.dummy.QuestionBoardDummy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@DataJpaTest
public class QuestionBoardTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    QuestionBoardRepository questionBoardRepository;

    MemberDummy memberDummy = new MemberDummy();

    QuestionBoardDummy questionBoardDummy;

    @BeforeEach
    public void setup(){
        memberRepository.save(memberDummy.toEntity());
        Member member = memberRepository.findAll().get(0);

        questionBoardDummy = new QuestionBoardDummy(member);
        questionBoardRepository.save(questionBoardDummy.toEntity());
    }

    @AfterEach
    public void cleanup(){
        questionBoardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 질문_작성조회(){
        QuestionBoard questionBoard = questionBoardRepository.findAll().get(0);
        assertThat(questionBoard.getQuestionBoardTitle()).isEqualTo(questionBoardDummy.getQuestionBoardTitle());
    }

    @Test
    public void 답변_작성(){
        Date serverDate = new Date();
        String answer = "답변";

        QuestionBoard questionBoard = questionBoardRepository.findAll().get(0);
        questionBoard.completeAnswer(answer,serverDate);
        questionBoardRepository.save(questionBoard);

        assertThat(questionBoardRepository.findAll().get(0).getQuestionBoardAnswer()).isEqualTo(answer);
    }
}
