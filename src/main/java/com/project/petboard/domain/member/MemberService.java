package com.project.petboard.domain.member;

import com.project.petboard.infrastructure.jwt.ResponseJwt;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.kakao.KakaoUtil;
import com.project.petboard.infrastructure.kakao.RequestKakao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    private final KakaoUtil kakaoUtil;

    private final JwtTokenUtil jwtTokenUtil;

    public void deleteMember(Long memberNumber) {
        memberRepository.deleteById(memberNumber);
    }

    public ResponseEntity<ResponseJwt> loginMember(String code) {
        RequestKakao requestKakao = kakaoUtil.getKakaoProfile(code);
        Member member = memberRepository.findByMemberSnsId(String.valueOf(requestKakao.getId()))
                .map(entity -> entity.kakaoProfileUpdate(requestKakao.getKakao_account()))
                .orElseGet(() -> saveMember(requestKakao));
        return createToken(member);
    }

    public ResponseEntity<ResponseJwt> createToken(Member member) {
        return jwtTokenUtil.responseHeaderToken(member.getMemberNumber());
    }

    public void saveRole(Member member) {
        roleRepository.save(new Role(member, "MEMBER"));
    }

    public Member saveMember(RequestKakao requestKakao) {
        memberRepository.save(requestKakao.toEntity());
        Member member = memberRepository.findByMemberSnsId(String.valueOf(requestKakao.getId())).get();
        saveRole(member);
        return member;
    }

}
