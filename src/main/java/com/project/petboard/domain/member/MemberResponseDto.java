package com.project.petboard.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NotNull
@Getter
@NoArgsConstructor
public class MemberResponseDto {

    @Positive(message = "필수로 요소가 필요합니다.")
    private Long memberNumber;

    @NotBlank(message = "필수로 요소가 필요합니다.")
    @Size(min = 2,max = 20,message = "2개 미만의 길이는 불가능합니다.")
    private String memberNickname;

    public MemberResponseDto(Member member){
        this.memberNumber = member.getMemberNumber();
        this.memberNickname = member.getMemberNickname();
    }

}
