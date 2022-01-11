package com.project.petboard.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NotNull
@Getter
@AllArgsConstructor
public class MemberResponseDto {

    @Positive(message = "필수로 요소가 필요합니다.")
    private final Long memberNumber;

    @NotBlank(message = "필수로 요소가 필요합니다.")
    @Size(min = 2,max = 20,message = "2개 미만의 길이는 불가능합니다.")
    private final String memberNickname;

    public MemberResponseDto(Member member){
        this.memberNumber = member.getMemberNumber();
        this.memberNickname = member.getMemberNickname();
    }

}
