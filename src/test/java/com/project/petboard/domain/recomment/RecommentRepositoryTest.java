package com.project.petboard.domain.recomment;


import com.project.petboard.domain.board.BoardRepository;
import com.project.petboard.domain.comment.CommentRepositoryTest;
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
public class RecommentRepositoryTest {

    @Autowired
    RecommentRepositoryTest repository;

    @Autowired
    CommentRepositoryTest commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Before
    public void setup(){

    }

    @After
    public void cleanup(){

    }

    @Test
    public void 대댓글_조회(){

    }

    @Test
    public void 대댓글_수정(){

    }

    @Test
    public void 대댓글_삭제(){

    }

}
