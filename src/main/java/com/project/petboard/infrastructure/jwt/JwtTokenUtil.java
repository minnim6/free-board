package com.project.petboard.infrastructure.jwt;

import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.member.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
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

    public JwtDto createToken(Long memberNumber) {
        long nowDate = (new Date()).getTime();
        Date tokenExpireDate = crateTokenExpireDate(nowDate, ACCESS_TOKEN_EXPIRE_TIME);

        return JwtDto.builder()
                .accessToken(crateAccessToken(memberNumber, tokenExpireDate))
                .refreshToken(createRefreshToken(nowDate))
                .assessTokenExpireTime(tokenExpireDate.getTime())
                .build();
    }

    public ResponseEntity<JwtDto> responseHeaderToken(Long memberNumber){
        return ResponseEntity.ok(createToken(memberNumber));
    }

    public Date crateTokenExpireDate(long nowDate, long accessTime) {
        return new Date(nowDate + accessTime);
    }

    public String createRefreshToken(long nowDate) {
        return Jwts.builder()
                .setExpiration(crateTokenExpireDate(nowDate, REFRESH_TOKEN_EXPIRE_TIME))
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
        System.out.println(request.getHeader("Authorization"));
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

    public Claims paresClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new JwtException("만료되었거나 올바르지 않은 토큰입니다.", e);
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = paresClaims(token);

        if (!isCheckAuthorities(claims)) {
            throw new RuntimeException();
        }
        Long memberNumber =  Long.valueOf(String.valueOf(claims.get(AUTHORITIES_KEY)));

        Collection<? extends GrantedAuthority> authorities = getAuthorities(memberNumber);

        UserDetails userDetails = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Long memberNumber) {
        List<Role> roles = memberRepository.findByMemberNumber(memberNumber).getMemberRole();
        System.out.println(roles.get(0).getRole());
        return memberRepository.findByMemberNumber(memberNumber)
                .getMemberRole()
                .stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    public boolean isCheckAuthorities(Claims claims) {
        return !(claims.get(AUTHORITIES_KEY) == null);
    }

}
