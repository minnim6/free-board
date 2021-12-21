package com.project.petboard.controller;

import com.project.petboard.appilcation.MemberController;
import com.project.petboard.domain.member.MemberService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@WebMvcTest( controllers = MemberController.class)
public class MemberControllerTest {

    @Mock
    MemberService memberService;



}
