package com.project.petboard.appilcation;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.jwt.ResponseJwt;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class JwtController {

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/jwt")
    public ResponseJwt requestToken(@RequestHeader("access_token") String accessToken , @RequestHeader("refresh_token") String refreshToken){
        return jwtTokenUtil.requestToken(accessToken, refreshToken);
    }

}
