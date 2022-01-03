package com.project.petboard.service;

import com.project.petboard.domain.report.Sanctions;
import com.project.petboard.domain.report.SanctionsDto;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

public class SanctionsServiceTest {
    SanctionsRepository sanctionsRepository = mock(SanctionsRepository.class);

    SanctionsService sanctionsService = new SanctionsService(sanctionsRepository);

    private Sanctions sanctions;

    private SanctionsDto sanctionsDto;

    private final String sanctionKey = "testKey";

    @BeforeEach
    public void setup(){
        sanctions = Sanctions.builder()
                .sanctionsKey("key")
                .sanctionsValue(5)
                .sanctionsContents("contents")
                .build();
        sanctionsDto = new SanctionsDto();
    }

    @DisplayName("제재규정 삭제 테스트")
    @Test
    public void deleteSanctionsTestShouldBeSuccess(){
        //when
        sanctionsRepository.deleteById(sanctionKey);
        //then
        verify(sanctionsRepository).deleteById(sanctionKey);
    }

    @DisplayName("제재규정 생성 테스트")
    @Test
    public void createSanctionsTestShouldBeSuccess(){
        //given
        given(sanctionsRepository.save(any())).willReturn(sanctions);
        given(sanctionsRepository.findBySanctionsKey(sanctionKey)).willReturn(sanctions);
        //when
        sanctionsService.createSanctions(sanctionsDto);
        //then
        assertThat(sanctionsRepository.findBySanctionsKey(sanctionKey)
                .getSanctionsContents()).isEqualTo("contents");
    }
}
