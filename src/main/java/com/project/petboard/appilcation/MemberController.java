package com.project.petboard.appilcation;

import com.project.petboard.domain.member.MemberService;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/kakao/login")
    public ResponseJwt loginMember(@RequestParam("code") String code){
        return memberService.loginMember(code);
    }

    @DeleteMapping("/member")
    public void deleteMember(@RequestParam("memberNumber")Long memberNumber){
        memberService.deleteMember(memberNumber);
    }

}
