package com.project.petboard.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public KakaoToken getKakaoToken(String code) {
        String accessToken = "";
        URI uri = URI.create("https://kauth.kakao.com/oauth/token");
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.set("grant_type", "authorization_code");
        parameters.set("client_id", "40c8e0dd35c6d1009d31116ba9218cec");
        parameters.set("redirect_uri", "http://localhost:8080/kakaologin");
        parameters.set("code", code);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);
        KakaoToken ks = restTemplate.postForObject("https://kauth.kakao.com/oauth/token", entity, KakaoToken.class);
        System.out.println(ks.toString());
        return ks;
    }

    public void saveMember(String code) {
        KakaoToken kakaoToken = getKakaoToken(code);
        URI uri = URI.create("https://kapi.kakao.com/v2/user/me");

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", Collections.singletonList("Bearer " + kakaoToken.getAccess_token()));

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);
      //  Map map = restTemplate.postForObject(uri, entity, Map.class);
        RequestKakao requestKakao = restTemplate.postForObject(uri, entity, RequestKakao.class);

        System.out.println(requestKakao.toString());
        System.out.println(requestKakao.getKakao_account().getProfile().getNickname());
        memberRepository.save(requestKakao.getKakao_account().toEntity());
    }
}
