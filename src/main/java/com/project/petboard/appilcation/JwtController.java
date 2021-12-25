package com.project.petboard.appilcation;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/jwt")
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/requestToken")
    public ResponseEntity<ResponseJwt> RequestToken(HttpServletRequest servletRequest){
        return ResponseEntity.ok(jwtTokenUtil.requestToken(servletRequest));
    }

}
