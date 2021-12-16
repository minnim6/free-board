package com.project.petboard.domain.token;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenService {

    @Value("${jwt.secret_key}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private final JwtTokenUtil jwtTokenUtil;

    private final TokenRepository tokenRepository;

    public ResponseEntity<ResponseTokenDto> RequestToken(HttpServletRequest servletRequest){
        String accessToken = servletRequest.getHeader("accessToken");
        String refreshToken = servletRequest.getHeader("refreshToken");

        RequestToken requestToken = new RequestToken(tokenRepository.findByRefreshToken(refreshToken));
        if(requestToken.getAccessToken().equals(accessToken)){
           if(new Date().before(requestToken.getRefreshTokenExpireTime())){
               Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
               Long memberNumber =  Long.valueOf(String.valueOf(claims.get("memberNumber")));
               Date tokenExpireDate = new Date(new Date().getTime()+ACCESS_TOKEN_EXPIRE_TIME);
               String responseAccessToken =  jwtTokenUtil.crateAccessToken(memberNumber,tokenExpireDate);
               return ResponseEntity.ok(new ResponseTokenDto(responseAccessToken,tokenExpireDate));
           }
        }
        throw new RuntimeException();
    }
    public boolean isCheckDate(){

    }

}
