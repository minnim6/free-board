package com.project.petboard.dummy;

import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.report.Report;
import com.project.petboard.domain.member.Member;
import lombok.Getter;

@Getter
public class ReportDummy {
    /*
     this.board = board;
        this.reportCategory = reportCategory;
        this.member = member;
        this.reportContents = reportContents;
     */

    private final Post post;
    private final Member member;
    private final String reportContents = "신고내용";
    private final String reportCategory = "카테고리";


    public ReportDummy(Post post, Member member){
        this.post = post;
        this.member = member;
    }

    public Report toEntity(){
        return Report.builder()
                .member(member)
                .post(post)
                .reportContents(reportContents)
                .reportCategory(reportCategory)
                .build();
    }

}
