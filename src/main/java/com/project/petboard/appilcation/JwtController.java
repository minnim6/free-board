package com.project.petboard.appilcation;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/jwt")
    public ResponseJwt RequestToken(@RequestHeader("access_token") String accessToken , @RequestHeader("refresh_token") String refreshToken){
        return jwtTokenUtil.requestToken(accessToken, refreshToken);
    }

}
