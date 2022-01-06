package com.project.petboard.appilcation;

import com.project.petboard.domain.member.MemberService;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/kakao/login")
    public ResponseJwt loginMember(@RequestParam("code") String code){
        return memberService.loginMember(code);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PatchMapping("/member")
    public void deleteMember(@RequestParam("memberNumber")Long memberNumber){
        memberService.deleteMember(memberNumber);
    }

}
