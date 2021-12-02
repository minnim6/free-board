package com.project.petboard.domain.post;

import com.project.petboard.domain.member.*;
import com.project.petboard.domain.report.ReportRepository;
import com.project.petboard.dummy.PostDummy;
import com.project.petboard.dummy.MemberDummy;
import com.project.petboard.dummy.ReportDummy;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReportRepository reportRepository;

    MemberDummy memberDummy = new MemberDummy();

    PostDummy postDummy;

    ReportDummy reportDummy;

    @BeforeEach
    public void setup() {
        memberRepository.save(memberDummy.toEntity());

        Member member = memberRepository.findAll().get(0);

        postDummy = new PostDummy(member);
        postRepository.save(postDummy.toEntity());

        Post post = postRepository.findAll().get(0);

    }

    @AfterEach
    public void cleanup() {
        reportRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 게시물_조회() {
        Post post = postRepository.findAll().get(0);
        assertThat(post.getPostTitle()).isEqualTo(postDummy.getPostTitle());
    }

    @Test
    public void 게시물작성자_조회() {
        Post post = postRepository.findAll().get(0);
        assertThat(post.getMember().getMemberNickname()).isEqualTo(memberDummy.getMemberNickname());
    }

    
}
