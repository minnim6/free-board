package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
