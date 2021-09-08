package com.project.petboard.domain.recomment;


import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.board.BoardRepository;
import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.comment.CommentRepositoryTest;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.dummy.BoardDummy;
import com.project.petboard.dummy.CommentDummy;
import com.project.petboard.dummy.MemberDummy;
import com.project.petboard.dummy.RecommentDummy;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
public class RecommentRepositoryTest {

    @Autowired
    RecommentRepository recommentRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    MemberDummy memberDummy = new MemberDummy();

    BoardDummy boardDummy;

    CommentDummy commentDummy;

    RecommentDummy recommentDummy;

    @BeforeEach
    public void setup(){
        memberRepository.save(memberDummy.toEntity());

        Member member = memberRepository.findAll().get(0);

        boardDummy = new BoardDummy(member);
        boardRepository.save(boardDummy.toEntity());

        Board board = boardRepository.findAll().get(0);

        commentDummy = new CommentDummy(board,member);
        commentRepository.save(commentDummy.toEntity());

        Comment comment = commentRepository.findAll().get(0);

        recommentDummy = new RecommentDummy(member,board,comment);
        recommentRepository.save(recommentDummy.toEntity());
    }

    @AfterEach
    public void cleanup(){
        recommentRepository.deleteAll();
        commentRepository.deleteAll();;
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 대댓글_조회(){
        Recomment recomment = recommentRepository.findAll().get(0);
        assertThat(recomment.getRecommentContents()).isEqualTo(recommentDummy.getRecommentContents());
    }

    @Test
    public void 대댓글_수정(){
        String updateContents = "내용변경";

        Recomment recomment = recommentRepository.findAll().get(0);
        recomment.update(updateContents);
        recommentRepository.save(recomment);

        assertThat(recommentRepository.findAll().get(0).getRecommentContents()).isEqualTo(updateContents);
    }

    @Test
    public void 대댓글_삭제(){
        Recomment recomment = recommentRepository.findAll().get(0);
        recommentRepository.delete(recomment);

        assertThrows(IndexOutOfBoundsException.class,()-> recommentRepository.findAll().get(0));
    }

}
