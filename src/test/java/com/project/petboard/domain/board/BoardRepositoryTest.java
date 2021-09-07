package com.project.petboard.domain.board;

import com.project.petboard.domain.member.*;
import com.project.petboard.dummy.BoardDummy;
import com.project.petboard.dummy.MemberDummy;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReportRepository repository;

    MemberDummy memberDummy = new MemberDummy();

    BoardDummy boardDummy;

    @Before
    public void setup() {
        memberRepository.save(memberDummy.toEntity());

        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);

        boardDummy = new BoardDummy(member);
        boardRepository.save(boardDummy.toEntity());
    }

    @After
    public void cleanup() {
        repository.deleteAll();
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 게시물_조회() {
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);
        assertThat(board.getBoardTitle()).isEqualTo(boardDummy.getBoardTitle());
    }

    @Test
    public void 게시물작성자_조회(){
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);
        assertThat(board.getMember().getMemberNickname()).isEqualTo(memberDummy.getMemberNickname());
    }

    @Test
    public void 게시물_수정() {
        String updateContents = "내용변경";
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);

        board.update(boardDummy.getBoardTitle(), updateContents, boardDummy.getBoardCategory());
        boardRepository.save(board);

        boardList = boardRepository.findAll();
        board = boardList.get(0);
        assertThat(board.getBoardContents()).isEqualTo(updateContents);
    }

    @Test
    public void 게시물_신고() {
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);

        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);

        repository.save(Report.builder()
                .board(board)
                .member(member)
                .reportCategory("욕설")
                .reportContents("시발")
                .build());
        repository.save(Report.builder()
                .board(board)
                .member(member)
                .reportCategory("욕설")
                .reportContents("시발")
                .build());
        List<Report> reportList = repository.findByBoard(board);
        log.info(String.valueOf(reportList.size()));
        if (reportList.size() > 1) {
            board.blind();
        }
        assertThat(board.getBoardStatus()).isEqualTo(BoardStatus.N);
    }

    @Test
    public void 게시물_삭제() {
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);

        boardRepository.delete(board);

        boardList = boardRepository.findAll();
        assertThat(boardList.isEmpty());
    }
}
