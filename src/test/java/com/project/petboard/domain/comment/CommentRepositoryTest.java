package com.project.petboard.domain.comment;


import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.board.BoardRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.MemberSingupCategory;
import com.project.petboard.domain.member.MemberStatus;
import com.project.petboard.dummy.BoardDummy;
import com.project.petboard.dummy.CommentDummy;
import com.project.petboard.dummy.MemberDummy;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    MemberDummy memberDummy = new MemberDummy();

    BoardDummy boardDummy;

    CommentDummy commentDummy;

    @Before
    public void setup(){
        memberRepository.save(memberDummy.toEntity());

        Member member = memberRepository.findAll().get(0);

        boardDummy = new BoardDummy(member);
        boardRepository.save(boardDummy.toEntity());

        Board board = boardRepository.findAll().get(0);

        commentDummy = new CommentDummy(board,member);
        commentRepository.save(commentDummy.toEntity());
    }

    @After
    public void cleanup(){
        commentRepository.deleteAll();;
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 댓글_조회(){
        Comment comment = commentRepository.findAll().get(0);

        assertThat(comment.getCommentContents()).isEqualTo(commentDummy.getCommentContents());
    }

    @Test
    public void 댓글_수정(){
        String updateCommentContents = "내용수정";

        Comment comment = commentRepository.findAll().get(0);

        comment.update(updateCommentContents);
        commentRepository.save(comment);

        assertThat(commentRepository.findAll().get(0).getCommentContents()).isEqualTo(updateCommentContents);
    }

    @Test
    public void 댓글_삭제(){
        Comment comment = commentRepository.findAll().get(0);
        Long commentNumber = comment.getCommentNumber();

        commentRepository.delete(comment);

        assertThrows(IndexOutOfBoundsException.class,()-> commentRepository.findAll().get(0));
    }

}
