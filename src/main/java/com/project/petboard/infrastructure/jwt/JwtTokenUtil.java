package com.project.petboard.infrastructure.jwt;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import com.project.petboard.infrastructure.exception.JwtErrorCode;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret_key}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    private final String AUTHORITIES_KEY = "memberNumber";

    private final MemberRepository memberRepository;

    @PostConstruct
    protected void settingsToken() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public ResponseJwt createToken(Member member) {

        long nowDate = (new Date()).getTime();
        Date tokenExpireDate = crateTokenExpireDate(nowDate, ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = crateAccessToken(member.getMemberNumber(), tokenExpireDate);

        Date refreshTokenExpireDate = crateTokenExpireDate(nowDate, REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = createRefreshToken(refreshTokenExpireDate);

        member.setMemberRefreshToke(refreshToken);
        member.setMemberRefreshTokenExpireTime(refreshTokenExpireDate);

        return ResponseJwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .assessTokenExpireTime(tokenExpireDate.getTime())
                .build();
    }

    private boolean isExistsMemberRefreshToken(String refreshToken){
      return memberRepository.existsByMemberRefreshToken(refreshToken);
    }

    public Date crateTokenExpireDate(long nowDate, long accessTime) {
        return new Date(nowDate + accessTime);
    }

    public String createRefreshToken(Date nowDate) {
        return Jwts.builder()
                .setExpiration(nowDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String crateAccessToken(Long memberNumber, Date tokenExpireDate) {
        return Jwts.builder()
                .setSubject(memberNumber.toString())
                .claim(AUTHORITIES_KEY, memberNumber.toString())
                .setExpiration(tokenExpireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean isValidateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // 가져온 토큰을 claims로 바꿈
            return !claims.getBody().getExpiration().before(new Date()); //-> 만료된 토큰일 경우 false 리턴
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims paresClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new JwtException("올바르지 않은 토큰입니다.", e);
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = paresClaims(token);
        if (!isCheckAuthorities(claims)) {
            throw new CustomErrorException(HttpErrorCode.BAD_REQUEST);
        }
        Long memberNumber =  Long.valueOf(String.valueOf(claims.get(AUTHORITIES_KEY)));

        Collection<? extends GrantedAuthority> authorities = getAuthorities(memberNumber);

        UserDetails userDetails = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Long memberNumber) {
        return memberRepository.findByMemberNumber(memberNumber)
                .getMemberRole()
                .stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    private boolean isCheckAuthorities(Claims claims) {
        return !(claims.get(AUTHORITIES_KEY) == null);
    }

    public ResponseJwt requestToken(String accessToken, String refreshToken) {
        if(isValidateToken(refreshToken)) {
            Long memberNumber = Long.valueOf(String.valueOf(getClaims(accessToken).get("memberNumber")));
            Date tokenExpireDate = createAccessTokenExpireDate();
            crateAccessToken(memberNumber, tokenExpireDate);
            return new ResponseJwt(accessToken,refreshToken ,tokenExpireDate.getTime());
        }
        throw new CustomErrorException(JwtErrorCode.TOKEN_EXPIRE);
    }

    private Date createAccessTokenExpireDate() {
        return new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME);
    }

    private Claims getClaims(String accessToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
    }

    private boolean isValidateDate(Date refreshTokenExpireTime) {
        return new Date().before(refreshTokenExpireTime);
    }
}