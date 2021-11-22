package com.project.petboard.domain.report;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import lombok.Getter;

import java.util.Date;

@Getter
public class ReportDto {

    private Long reportNumber;

    private Post post;

    private String reportCategory;

    private Member member;

    private String reportContents;

    private Date reportCreateDate;

    public Report toEntity(){
        return Report.builder()
                .member(member)
                .post(post)
                .reportContents(reportContents)
                .reportCategory(reportCategory)
                .build();
    }
}
