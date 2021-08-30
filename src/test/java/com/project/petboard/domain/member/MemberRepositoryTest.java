package com.project.petboard.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @After
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Before
    public void setup() {
        memberRepository.save(Member.builder()
                .memberNickname("회원1")
                .memberSingupCategory(MemberSingupCategory.KAKAO) //수정 필요.
                .memberStatus(MemberStatus.Y)
                .build());
    }

    @Test
    public void 회원정보_조회(){
        Optional<Member> member = memberRepository.findById(1L);
        member.ifPresent(selectedMember -> {
           assertThat(selectedMember.getMemberNickname()).isEqualTo("회원1");
        });
    }

    @Test
    public void 닉네임변경(){
        String nickname = "회원2";
        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);
        member.NicknameChange(nickname);
        memberRepository.save(member);
        assertThat(member.getMemberNickname()).isEqualTo(nickname);
    }

    @Test
    public void 회원탈퇴() {
        Optional<Member> member = memberRepository.findById(1L);
        member.ifPresent(selectedMember -> {
            memberRepository.delete(selectedMember);
        });
        if(!member.isPresent()){
            log.info("삭제완료");
        }
    }


}
