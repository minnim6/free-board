package com.project.petboard.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Optional<Member> testMember(Long memberNumber){
      return memberRepository.findById(memberNumber);
    }
}
