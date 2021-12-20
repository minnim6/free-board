package com.project.petboard.domain.token;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    @Value("${jwt.secret_key}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private final JwtTokenUtil jwtTokenUtil;

    private final TokenRepository tokenRepository;

    public ResponseTokenDto requestToken(HttpServletRequest servletRequest){

        String accessToken = servletRequest.getHeader("accessToken");
        String refreshToken = servletRequest.getHeader("refreshToken");

       /* if(requestToken.getAccessToken().equals(accessToken)&&isValidateDate(requestToken.getRefreshTokenExpireTime())){
           if(new Date().before(requestToken.getRefreshTokenExpireTime())){
               Claims claims = getClaims(accessToken);
               Long memberNumber =  Long.valueOf(String.valueOf(claims.get("memberNumber")));
               Date tokenExpireDate = createTokenExpireDate();
               String responseAccessToken =  jwtTokenUtil.crateAccessToken(memberNumber,createTokenExpireDate());
               return new ResponseTokenDto(responseAccessToken,tokenExpireDate);
           }
        }

        */
        throw new RuntimeException();
    }

    public Date createTokenExpireDate(){
        return new Date(new Date().getTime()+ACCESS_TOKEN_EXPIRE_TIME);
    }

    public Claims getClaims(String accessToken){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
    }



    public boolean isValidateDate(Date refreshTokenExpireTime){
        return new Date().before(refreshTokenExpireTime);
    }

}
