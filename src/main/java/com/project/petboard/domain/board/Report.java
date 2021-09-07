package com.project.petboard.domain.board;

import com.project.petboard.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Report {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long ReportNumber;

    @ManyToOne
    @JoinColumn
    private Board board;

    private String ReportCategory;

    @ManyToOne
    @JoinColumn
    private Member member;

    private String ReportContents;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date ReportCreateDate;

    @Builder
    public Report(Board board,String reportCategory,Member member,String reportContents){
        this.board = board;
        this.ReportCategory = reportCategory;
        this.member = member;
        this.ReportContents = reportContents;
    }

}