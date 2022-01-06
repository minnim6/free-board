package com.project.petboard.domain.report;

import com.project.petboard.domain.recomment.Recomment;
import com.project.petboard.domain.recomment.ReocommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Transactional
@Service
public class SanctionsService {
    private final SanctionsRepository sanctionsRepository;

    public void createSanctions(SanctionsDto sanctionsDto) {
        sanctionsRepository.save(sanctionsDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<SanctionsDto> fetchSanctionsList(Pageable pageable) {
        return sanctionsRepository.findAll(pageable).map(new Function<Sanctions, SanctionsDto>() {
            @Override
            public SanctionsDto apply(Sanctions sanctions) {
                // Conversion logic
                return new SanctionsDto(sanctions);
            }
        });
    }

    public void deleteSanctions(String sanctionsKey) {
        sanctionsRepository.deleteById(sanctionsKey);
    }
}
