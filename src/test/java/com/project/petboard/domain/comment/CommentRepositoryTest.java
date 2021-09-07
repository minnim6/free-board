package com.project.petboard.domain.comment;


import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.board.BoardRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.MemberSingupCategory;
import com.project.petboard.domain.member.MemberStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void setup(){
        //댓글 작성 코드 setup 대체
        memberRepository.save(Member.builder()
                .memberNickname("회원1")
                .memberSingupCategory(MemberSingupCategory.KAKAO) //수정 필요.
                .memberStatus(MemberStatus.Y)
                .build());
        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);
        boardRepository.save(Board.builder()
                .member(member)
                .boardCategory("카테고리")
                .boardTitle("제목")
                .boardContents("내용")
                .build());
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);
        commentRepository.save(Comment.builder()
                .board(board)
                .member(member)
                .commentContents("댓글")
                .build());
    }

    @After
    public void cleanup(){
        commentRepository.deleteAll();;
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 댓글_조회(){
        List <Comment> commentList = commentRepository.findAll();
        Comment comment = commentList.get(0);
        assertThat(comment.getCommentContents()).isEqualTo("댓글");
    }

    @Test
    public void 댓글_수정(){
        List <Comment> commentList = commentRepository.findAll();
        Comment comment = commentList.get(0);
        comment.update("수정된 내용");
        commentRepository.save(comment);
        commentList = commentRepository.findAll();
        comment = commentList.get(0);
        assertThat(comment.getCommentContents()).isEqualTo("수정된 내용");
    }

    @Test
    public void 댓글_삭제(){
        List <Comment> commentList = commentRepository.findAll();
        Comment comment = commentList.get(0);
        commentRepository.delete(comment);
        commentList = commentRepository.findAll();
        assertThat(commentList.isEmpty());
    }

}
