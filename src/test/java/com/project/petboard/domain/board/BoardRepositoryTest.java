package com.project.petboard.domain.board;

import com.project.petboard.domain.member.*;
import com.project.petboard.dummy.BoardDummy;
import com.project.petboard.dummy.MemberDummy;
import com.project.petboard.dummy.ReportDummy;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReportRepository reportRepository;

    MemberDummy memberDummy = new MemberDummy();

    BoardDummy boardDummy;

    ReportDummy reportDummy;

    @Before
    public void setup() {
        memberRepository.save(memberDummy.toEntity());

        Member member = memberRepository.findAll().get(0);

        boardDummy = new BoardDummy(member);
        boardRepository.save(boardDummy.toEntity());

        Board board = boardRepository.findAll().get(0);

        reportDummy = new ReportDummy(board,member);
        reportRepository.save(reportDummy.toEntity());
    }

    @After
    public void cleanup() {
        reportRepository.deleteAll();
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 게시물_조회() {
        Board board = boardRepository.findAll().get(0);
        assertThat(board.getBoardTitle()).isEqualTo(boardDummy.getBoardTitle());
    }

    @Test
    public void 게시물작성자_조회(){
        Board board = boardRepository.findAll().get(0);
        assertThat(board.getMember().getMemberNickname()).isEqualTo(memberDummy.getMemberNickname());
    }

    @Test
    public void 게시물_수정() {
        String updateContents = "내용변경";
        Board board = boardRepository.findAll().get(0);

        board.update(boardDummy.getBoardTitle(), updateContents, boardDummy.getBoardCategory());
        boardRepository.save(board);

        assertThat(boardRepository.findAll().get(0).getBoardContents()).isEqualTo(updateContents);
    }

    @Test
    public void 게시물_신고() {
        int testBlindSize = 0;

        Board board = boardRepository.findAll().get(0);

        List<Report> reportList = reportRepository.findByBoard(board);

        if (reportList.size() > testBlindSize) {
            board.blind();
        }

        assertThat(board.getBoardStatus()).isEqualTo(BoardStatus.N);
    }

    @Test
    public void 게시물_삭제() {
        Board board = boardRepository.findAll().get(0);

        boardRepository.delete(board);

        assertThrows(IndexOutOfBoundsException.class,()-> boardRepository.findAll().get(0));
    }
}
