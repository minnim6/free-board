package com.project.petboard.service;

import com.project.petboard.domain.page.RequestPage;
import com.project.petboard.domain.report.Sanctions;
import com.project.petboard.domain.report.SanctionsDto;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @DisplayName("페이지 가져오기 테스트")
    @Test
    public void getSanctionsPageTestShouldBeSuccess() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        List<Sanctions> sanctionsList = new ArrayList<>();
        sanctionsList.add(sanctions);
        sanctionsList.add(sanctions);
        Page<Sanctions> sanctionsPage = new PageImpl<>(sanctionsList);
        given(sanctionsRepository.findAll(pageable)).willReturn(sanctionsPage);
        RequestPage requestPage = new RequestPage(0,10);
        //when
        List<SanctionsDto> sanctionsDtos = sanctionsService.fetchSanctionsList(requestPage);
        //then
        assertThat(sanctionsDtos.size()).isEqualTo(2);
    }

}
