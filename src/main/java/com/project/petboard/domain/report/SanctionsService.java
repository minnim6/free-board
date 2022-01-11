package com.project.petboard.domain.report;

import com.project.petboard.domain.page.RequestPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SanctionsService {
    private final SanctionsRepository sanctionsRepository;

    public void createSanctions(SanctionsDto sanctionsDto) {
        sanctionsRepository.save(sanctionsDto.toEntity());
    }

    @Transactional(readOnly = true)
    public List<SanctionsDto> fetchSanctionsList(RequestPage requestPage) {
            Pageable pageable = PageRequest.of(requestPage.getPage(),requestPage.getSize());
            List<SanctionsDto> page = sanctionsRepository.findAll(pageable).getContent().stream()
                    .map(sanctions -> new SanctionsDto(sanctions))
                    .collect(Collectors.toList());
            return page;
    }

    public void deleteSanctions(String sanctionsKey) {
        sanctionsRepository.deleteById(sanctionsKey);
    }
}
