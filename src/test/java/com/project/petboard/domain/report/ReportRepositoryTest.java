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

    @BeforeEach
    public void setup() {
        memberRepository.save(memberDummy.toEntity());

        member = memberRepository.findAll().get(0);

        postDummy = new PostDummy(member);
        postRepository.save(postDummy.toEntity());

        post = postRepository.findAll().get(0);

        sanctionsRepository.save(sanctionsDummy.toEntity());
    }

    @Test
    public void 게시물_신고() {
        /*
        만일 한번이상 회원이 신고한적이 있다면,
        이미 신고처리 되었다고 알려주고,
        신고한적이 없다면 , 신고횟수에 추가해주고 ,
        블라인드 기준이된다면 블라인드 처리 .
         */

        if (!reportRepository.existsByMemberAndPost(member, post)) {
            post.addReportCount();
            postRepository.save(post);
            post = postRepository.findAll().get(0);
            int postReportCount = fetchPostReportCount(post.getPostNumber());
            int sanctionsValue = fetchSanctionsValue("게시글가리기");
            if(postReportCount>=sanctionsValue){

            }
        } else {

        }

    }

    public void 

    //신고 처리 로직
    public void saveReport(Post post, Member member){
        reportDummy = new ReportDummy(post, member);
        reportRepository.save(reportDummy.toEntity());
    }

    public int fetchPostReportCount(Long postId){
       return postRepository.findById(postId).get().getPostReportCount();
    }

    public int fetchSanctionsValue(String sanctionsKey){
        Optional<Sanctions> sanctions = sanctionsRepository.findById(sanctionsKey);
        return sanctions.get().getSanctionsValue();
    }
}
