package com.project.petboard.domain.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SanctionsService {
    private final SanctionsRepository sanctionsRepository;

    public void createSanctions(SanctionsDto sanctionsDto){
        sanctionsRepository.save(sanctionsDto.toEntity());
    }
    public List<Sanctions> fetchSanctionsList(){
        return sanctionsRepository.findAll();
    }
    public void deleteSanctions(String sanctionsKey){
        sanctionsRepository.deleteById(sanctionsKey);
    }
}
