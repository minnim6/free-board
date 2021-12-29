package com.project.petboard.appilcation;

import com.project.petboard.domain.member.MemberService;
import com.project.petboard.infrastructure.jwt.RequestJwt;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/kakaologin")
    public RequestJwt loginMember(@RequestParam("code") String code){
        return memberService.loginMember(code);
    }

    @DeleteMapping("/deleteMember")
    public void deleteMember(@RequestParam("memberNumber")Long memberNumber){
        memberService.deleteMember(memberNumber);
    }

}
