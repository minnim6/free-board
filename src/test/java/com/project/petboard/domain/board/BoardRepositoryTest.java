package com.project.petboard.domain.board;

import com.project.petboard.domain.member.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void setup() {
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
        assertThat(board.getBoardTitle()).isEqualTo("제목");
        assertThat(board.getMember().getMemberNickname()).isEqualTo("회원1");
    }

    @Test
    public void 게시물_수정() {
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);
        board.Update("제목", "내용변경", "카테고리변경");
        boardRepository.save(board);
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
       List <Report> reportList = repository.findByBoard(board);
       log.info(String.valueOf(reportList.size()));
       if(reportList.size()>1){
           board.blind();
       }
       assertThat(board.getBoardStatus()).isEqualTo(BoardStatus.N);
    }

    @Test
    public void 게시물_삭제(){
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);
        boardRepository.delete(board);
        boardList = boardRepository.findAll();
        if(boardList.isEmpty()){
            log.info("삭제성공");
        }
    }
}
