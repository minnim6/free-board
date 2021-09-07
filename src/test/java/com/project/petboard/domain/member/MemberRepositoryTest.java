package com.project.petboard.domain.member;

import com.project.petboard.dummy.MemberDummy;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
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
        Member member = memberRepository.findAll().get(0);

        assertThat(member.getMemberNickname()).isEqualTo(memberDummy.getMemberNickname());
    }

    @Test
    public void 닉네임변경() {
        String nickname = "회원2";

        Member member = memberRepository.findAll().get(0);

        member.nicknameChange(nickname);
        memberRepository.save(member);

        assertThat(member.getMemberNickname()).isEqualTo(nickname);
    }

    @Test
    public void 회원탈퇴() {
        Member member = memberRepository.findAll().get(0);

        memberRepository.delete(member);

        assertThrows(IndexOutOfBoundsException.class,()-> memberRepository.findAll().get(0));
    }

}
