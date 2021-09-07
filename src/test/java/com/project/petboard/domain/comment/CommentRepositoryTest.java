package com.project.petboard.domain.comment;


import com.project.petboard.domain.board.BoardRepository;
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
public class CommentRepositoryTest {

    @Autowired
    CommentRepositoryTest commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void setup(){
        //댓글 작성 코드 setup 대체
    }

    @After
    public void cleanup(){

    }

    @Test
    public void 댓글_조회(){

    }

    @Test
    public void 댓글_수정(){
        //date update check !
    }

    @Test
    public void 댓글_삭제(){

    }

}
