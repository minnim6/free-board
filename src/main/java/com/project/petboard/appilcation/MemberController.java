package com.project.petboard.appilcation;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.MemberService;
import com.project.petboard.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/getMember")
    public Optional<Member> fetchMember(@RequestParam("memberNumber") Long memberNumber){
        return memberService.testMember(memberNumber);
    }
}
