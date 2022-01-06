package com.project.petboard.domain.report;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NotNull
@NoArgsConstructor
@Getter
public class SanctionsDto {

    @NotBlank
    @Size(min = 1,max = 10,message = "최소 1글자 이상 , 최대 10글자 미만입니다.")
    private String sanctionsKey;

    @Positive(message = "필수로 요소가 필요합니다.")
    private int sanctionsValue;

    @Size(min = 10,max = 40,message = "최소 10글자 이상 , 최대 40글자 미만입니다.")
    private String sanctionsContents;

    public Sanctions toEntity(){
        return Sanctions.builder()
                .sanctionsKey(this.sanctionsKey)
                .sanctionsValue(this.sanctionsValue)
                .sanctionsContents(this.getSanctionsContents())
                .build();
    }

    public SanctionsDto(Sanctions sanctions){
        this.sanctionsKey = sanctions.getSanctionsKey();
        this.sanctionsValue = sanctions.getSanctionsValue();
        this.sanctionsContents = sanctions.getSanctionsContents();
    }

}
