package com.project.petboard.spock

import com.project.petboard.domain.member.Member
import com.project.petboard.domain.member.MemberRepository
import com.project.petboard.domain.post.Post
import com.project.petboard.domain.post.PostRepository
import com.project.petboard.domain.report.Report
import com.project.petboard.domain.report.ReportRepository
import com.project.petboard.domain.report.Sanctions
import com.project.petboard.domain.report.SanctionsRepository
import com.project.petboard.dummy.MemberDummy
import com.project.petboard.dummy.PostDummy
import com.project.petboard.dummy.ReportDummy
import com.project.petboard.dummy.SanctionsDummy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.transaction.Transactional

@DataJpaTest
class ReportRepositoryTest extends Specification {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    SanctionsRepository sanctionsRepository;

    @Autowired
    EntityManager entityManager;

    MemberDummy memberDummy = new MemberDummy();

    PostDummy postDummy;

    ReportDummy reportDummy;

    SanctionsDummy sanctionsDummy = new SanctionsDummy();

    Member member;

    Post post;

    Report report;

    def setup() {
        memberRepository.save(memberDummy.toEntity());

        member = memberRepository.findAll().get(0);

        postDummy = new PostDummy(member);
        postRepository.save(postDummy.toEntity());

        post = postRepository.findAll().get(0);

        // sanctionsRepository.save(sanctionsDummy.toEntity());

        reportDummy = new ReportDummy(post, member);
        reportRepository.save(reportDummy.toEntity());
    }

    def "신고키에대한 값 가져오기"() {

        given: //setup  사전에 해야할 행위
        Sanctions postSanctions = new Sanctions().builder()
                .sanctionsKey("게시물").sanctionsContents("게시물 정지기준").sanctionsValue(1).build()
        sanctionsRepository.save(postSanctions)

        expect: //when(실행) + then(검증)
        sanctionsRepository.findById(key).get().getSanctionsValue() == value

        where:
        key   | value
        "게시물" | 1

    }
}
