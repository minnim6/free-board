package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
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
public class Recomment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long recommentNumber;

    @ManyToOne
    @JoinColumn(name = "post_number")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_number")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_number")
    private Comment comment;

    private String recommentContents;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = false)
    @CreationTimestamp
    private Date recommentCreateDate;

    @Temporal(TemporalType.DATE)
    @Column(insertable = true,updatable = true)
    @CreationTimestamp
    private Date recommentAmendDate;

    @Builder
    public Recomment(Member member, Post post, Comment comment, String recommentContents){
        this.member = member;
        this.post = post;
        this.comment = comment;
        this.recommentContents = recommentContents;
    }

    public void update(String recommentContents){
        this.recommentContents = recommentContents;
    }
}
