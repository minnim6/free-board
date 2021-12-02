package com.project.petboard.domain.report;

import lombok.Getter;

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

}
