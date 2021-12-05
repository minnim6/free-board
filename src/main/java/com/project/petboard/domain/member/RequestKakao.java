package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class RequestKakao {
    /*
    id	Long	회원번호	O
has_signed_up	Boolean	자동 연결 설정을 비활성화한 경우만 존재
연결하기 호출의 완료 여부
false: 연결 대기(Preregistered) 상태
true: 연결(Registered) 상태	X
connected_at	Datetime	서비스에 연결 완료된 시각, UTC*	X
synched_at	Datetime	카카오싱크 간편가입을 통해 로그인한 시각, UTC*	X
properties	JSON	사용자 프로퍼티(Property)
설정하기 > 사용자 프로퍼티 참고	X
kakao_account	JSON	카카오계정 정보
     */
    private KakaoAccount kakao_account;
}
