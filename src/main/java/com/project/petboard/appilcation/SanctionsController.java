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
@RequestMapping("/Sanctions")
public class SanctionsController {

    private SanctionsService sanctionsService;

    @PostMapping
    public void createSanctions(SanctionsDto sanctionsDto) {
        sanctionsService.createSanctions(sanctionsDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Sanctions> fetchSanctionsList() {
        return sanctionsService.fetchSanctionsList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "deleteSanctions")
    public void deleteSanctions(String sanctionsKey) {
        sanctionsService.deleteSanctions(sanctionsKey);
    }
}
