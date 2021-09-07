package com.project.petboard.domain.member;

import com.project.petboard.dummy.MemberDummy;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    MemberDummy memberDummy = new MemberDummy();

    @After
    public void cleanup() {
        memberRepository.deleteAll();
    }

    @Before
    public void setup() { memberRepository.save(memberDummy.toEntity()); }

    @Test
    public void 회원정보_조회() {
        List<Member> memberList = memberRepository.findAll();

        Member member = memberList.get(0);

        assertThat(member.getMemberNickname()).isEqualTo(memberDummy.getMemberNickname());
    }

    @Test
    public void 닉네임변경() {
        String nickname = "회원2";

        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);

        member.nicknameChange(nickname);

        memberRepository.save(member);
        assertThat(member.getMemberNickname()).isEqualTo(nickname);
    }

    @Test
    public void 회원탈퇴() {
        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);

        memberRepository.delete(member);

        memberList = memberRepository.findAll();
        assertThat(memberList.isEmpty());
    }

}
