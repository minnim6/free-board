package com.project.petboard.appilcation;

import com.project.petboard.domain.report.Sanctions;
import com.project.petboard.domain.report.SanctionsDto;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class SanctionsController {

    private SanctionsService sanctionsService;

    @PostMapping("/sanctions")
    public void createSanctions(SanctionsDto sanctionsDto) {
        sanctionsService.createSanctions(sanctionsDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sanctions/page")
    public List<Sanctions> fetchSanctionsList() {
        return sanctionsService.fetchSanctionsList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/sanctions")
    public void deleteSanctions(String sanctionsKey) {
        sanctionsService.deleteSanctions(sanctionsKey);
    }
}
