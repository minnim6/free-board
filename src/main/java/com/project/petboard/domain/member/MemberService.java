package com.project.petboard.domain.member;

import lombok.RequiredArgsConstructor;
import org.h2.util.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public String getKakaoToken(String code){
        String accessToken = "";
        URI uri = URI.create("https://kauth.kakao.com/oauth/token");
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String ,Object> parameters = new LinkedMultiValueMap<String,Object>();
        parameters.set("grant_type","authorization_code");
        parameters.set("client_id","40c8e0dd35c6d1009d31116ba9218cec");
        parameters.set("redirect_uri","http://localhost:8080/kakaologin");
        parameters.set("code",code);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);
        ResponseEntity<Map> rs = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", entity, Map.class);
        return  rs.getBody().get("access_token").toString();
    }
    public void saveMember(String code){
        String authorization = getKakaoToken(code);
       URI uri = URI.create("https://kapi.kakao.com/v2/user/me");
       RestTemplate restTemplate = new RestTemplate();
       MultiValueMap<String,Object> parameters = new LinkedMultiValueMap<String,Object>();
       parameters.set("Authorization","Bearer "+authorization);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);


    }
}
