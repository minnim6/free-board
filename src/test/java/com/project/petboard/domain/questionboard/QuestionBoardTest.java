package com.project.petboard.domain.questionboard;


import com.project.petboard.domain.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionBoardTest {

    @Autowired
    MemberRepository memberRepository;

    @Before
    public void setup(){

    }

    @After
    public void cleanup(){

    }

    @Test
    public void 질문_작성조회(){

    }

    @Test
    public void 답변_작성(){

    }
}
