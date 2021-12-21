package com.project.petboard.domain.token;

import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    public ResponseTokenDto requestToken(HttpServletRequest servletRequest) {
        String accessToken = servletRequest.getHeader("accessToken");
        String refreshToken = servletRequest.getHeader("refreshToken");
        if (tokenRepository.existsByRefreshToken(refreshToken)) {
            if (jwtTokenUtil.isValidateToken(refreshToken)) {
                tokenRepository.deleteByRefreshToken(refreshToken);
                throw new RuntimeException();
            }
            Long memberNumber = Long.valueOf(String.valueOf(getClaims(accessToken).get("memberNumber")));
            Date tokenExpireDate = createTokenExpireDate();
            jwtTokenUtil.crateAccessToken(memberNumber, tokenExpireDate);
            return new ResponseTokenDto(accessToken, tokenExpireDate);
        }
        throw new RuntimeException();
    }

    public Date createTokenExpireDate() {
        return new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME);
    }

    public Claims getClaims(String accessToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
    }


    public boolean isValidateDate(Date refreshTokenExpireTime) {
        return new Date().before(refreshTokenExpireTime);
    }

}
