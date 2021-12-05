package com.project.petboard.infrastructure;

import com.project.petboard.domain.member.kakao.KakaoAccount;
import com.project.petboard.domain.member.kakao.KakaoToken;
import com.project.petboard.domain.member.kakao.RequestKakao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@Component
public class KakaoUtil {

    private final URI KAKAO_USER_URI = URI.create("https://kapi.kakao.com/v2/user/me");

    private final URI KAKAO_TOKEN_URI = URI.create("https://kauth.kakao.com/oauth/token");

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUrl;

    public KakaoToken getKakaoToken(String code) {

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();

        parameters.set("grant_type", "authorization_code");
        parameters.set("client_id", clientId);
        parameters.set("redirect_uri", redirectUrl);
        parameters.set("code", code);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);

        return postRestTemplate(KAKAO_TOKEN_URI, entity, KakaoToken.class);
    }

    public KakaoAccount getKakaoProfile(String code) {

        KakaoToken kakaoToken = getKakaoToken(code);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();

        headers.put("Authorization", Collections.singletonList("Bearer " + kakaoToken.getAccess_token()));

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);
        RequestKakao requestKakao = postRestTemplate(KAKAO_USER_URI, entity, RequestKakao.class);

        //TODO:에러 처리 로직 추가
        return requestKakao.getKakao_account();

    }

    public void logoutKakao(){

    }

    public <T> T postRestTemplate(URI uri, Object request, Class<T> tClass) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(uri, request, tClass);
    }
}
