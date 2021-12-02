package com.project.petboard.appilcation;

import com.project.petboard.domain.report.Sanctions;
import com.project.petboard.domain.report.SanctionsDto;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import lombok.AllArgsConstructor;
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

    @GetMapping
    public List<Sanctions> fetchSanctionsList() {
        return sanctionsService.fetchSanctionsList();
    }

    @DeleteMapping(value = "deleteSanctions")
    public void deleteSanctions(String sanctionsKey) {
        sanctionsService.deleteSanctions(sanctionsKey);
    }
}
