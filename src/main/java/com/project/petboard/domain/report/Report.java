package com.project.petboard.domain.report;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import lombok.Builder;
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
    private Long reportNumber;

    @ManyToOne
    @JoinColumn
    private Post post;

    private String reportCategory;

    @ManyToOne
    @JoinColumn
    private Member member;

    private String reportContents;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date reportCreateDate;

    @Builder
    public Report(Post post, String reportCategory, Member member, String reportContents){
        this.post = post;
        this.reportCategory = reportCategory;
        this.member = member;
        this.reportContents = reportContents;
    }

}
