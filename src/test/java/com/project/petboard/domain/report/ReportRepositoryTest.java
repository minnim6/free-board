package com.project.petboard.domain.report;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.dummy.MemberDummy;
import com.project.petboard.dummy.PostDummy;
import com.project.petboard.dummy.ReportDummy;
import com.project.petboard.dummy.SanctionsDummy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@Slf4j
@DataJpaTest
public class ReportRepositoryTest {
    /*
    member
    board
     */
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    SanctionsRepository sanctionsRepository;

    MemberDummy memberDummy = new MemberDummy();

    PostDummy postDummy;

    ReportDummy reportDummy;

    SanctionsDummy sanctionsDummy = new SanctionsDummy();

    Member member;

    Post post;

    Report report;

    @BeforeEach
    public void setup() {
        memberRepository.save(memberDummy.toEntity());

        member = memberRepository.findAll().get(0);

        postDummy = new PostDummy(member);
        postRepository.save(postDummy.toEntity());

        post = postRepository.findAll().get(0);

        sanctionsRepository.save(sanctionsDummy.toEntity());

        reportDummy = new ReportDummy(post, member);
        reportRepository.save(reportDummy.toEntity());
    }

    public void 게시물_신고횟수추가() {
        post.addReportCount();
        postRepository.save(post);
    }

    public void 신고_중복체크() {
        reportRepository.existsByMemberAndPost(member, post);
    }



    public void 신고키_값가져오기() {

    }
}
