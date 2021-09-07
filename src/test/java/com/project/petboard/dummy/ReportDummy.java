package com.project.petboard.dummy;

import com.project.petboard.domain.board.Board;
import com.project.petboard.domain.board.Report;
import com.project.petboard.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReportDummy {
    /*
     this.board = board;
        this.reportCategory = reportCategory;
        this.member = member;
        this.reportContents = reportContents;
     */

    private final Board board;
    private final Member member;
    private final String reportContents = "신고내용";
    private final String reportCategory = "카테고리";


    public ReportDummy(Board board,Member member){
        this.board = board;
        this.member = member;
    }

    public Report toEntity(){
        return Report.builder()
                .member(member)
                .board(board)
                .reportContents(reportContents)
                .reportCategory(reportCategory)
                .build();
    }

}
