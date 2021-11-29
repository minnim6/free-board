package com.project.petboard.dummy;

import com.project.petboard.domain.report.Sanctions;

public class SanctionsDummy {

    private String sanctionsKey = "게시글가리기";

    private int sanctionsValue = 1;

    private String sanctionsContents = "게시글에 대한 정지 기준";

    public Sanctions toEntity(){
        return Sanctions.builder()
                .sanctionsKey(sanctionsKey)
                .sanctionsValue(sanctionsValue)
                .sanctionsContents(sanctionsContents)
                .build();
    }
}
