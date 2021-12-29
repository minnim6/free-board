package com.project.petboard.appilcation;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.RequestJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/jwt")
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/requestToken")
    public RequestJwt RequestToken(@RequestHeader("access_token") String accessToken , @RequestHeader("refresh_token") String refreshToken){
        return jwtTokenUtil.requestToken(accessToken, refreshToken);
    }

}
