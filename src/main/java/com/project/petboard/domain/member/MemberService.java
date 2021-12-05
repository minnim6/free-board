package com.project.petboard.domain.member;

import com.project.petboard.domain.member.kakao.KakaoAccount;
import com.project.petboard.infrastructure.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final KakaoUtil kakaoUtil;

    public void deleteMember(Long memberNumber){
        memberRepository.deleteById(memberNumber);
    }

    public void loginMember(String code){
        KakaoAccount kakaoAccount = kakaoUtil.getKakaoProfile(code);
        if(!isCheckOverlapMember(kakaoAccount.getEmail())){
            saveMember(kakaoAccount);
        }else {
            throw new RuntimeException();
        }
    }

    public boolean isCheckOverlapMember(String email){
        return memberRepository.existsByMemberEmail(email);
    }

    public void saveMember(KakaoAccount kakaoAccount){
        memberRepository.save(kakaoAccount.toEntity());
    }
}
