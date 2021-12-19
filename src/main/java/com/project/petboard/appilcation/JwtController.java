package com.project.petboard.appilcation;

import com.project.petboard.domain.token.RequestToken;
import com.project.petboard.domain.token.ResponseTokenDto;
import com.project.petboard.domain.token.TokenRepository;
import com.project.petboard.domain.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/jwt")
@RestController
@RequiredArgsConstructor
public class JwtController {

    private final TokenService tokenService;

    @RequestMapping("/requestToken")
    public ResponseEntity<ResponseTokenDto> RequestToken(HttpServletRequest servletRequest){
        return ResponseEntity.ok(tokenService.requestToken(servletRequest));
    }

}
