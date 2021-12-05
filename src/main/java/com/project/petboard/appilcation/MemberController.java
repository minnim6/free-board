package com.project.petboard.appilcation;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.MemberService;
import com.project.petboard.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/kakaologin")
    public void loginMember(@RequestParam("code") String code) {
        memberService.loginMember(code);
    }

    @GetMapping("/deleteMember")
    public void deleteMember(@RequestParam("memberNumber")Long memberNumber){
        memberService.deleteMember(memberNumber);
    }

}
