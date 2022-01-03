package com.project.petboard.domain.report;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SanctionsDto {

    private String sanctionsKey;

    private int sanctionsValue;

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
