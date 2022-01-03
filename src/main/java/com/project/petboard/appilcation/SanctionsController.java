package com.project.petboard.appilcation;

import com.project.petboard.domain.report.Sanctions;
import com.project.petboard.domain.report.SanctionsDto;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
@RestController
public class SanctionsController {

    private SanctionsService sanctionsService;

    @PostMapping("/sanctions")
    public void createSanctions(SanctionsDto sanctionsDto) {
        sanctionsService.createSanctions(sanctionsDto);
    }

    @GetMapping("/sanctions/page")
    public Page<SanctionsDto> fetchSanctionsList(Pageable pageable) {
        return sanctionsService.fetchSanctionsList(pageable);
    }


    @DeleteMapping("/sanctions")
    public void deleteSanctions(String sanctionsKey) {
        sanctionsService.deleteSanctions(sanctionsKey);
    }
}
