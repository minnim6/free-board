package com.project.petboard.appilcation;

import com.project.petboard.domain.report.SanctionsDto;
import com.project.petboard.domain.report.SanctionsService;
import com.project.petboard.infrastructure.exception.RequestErrorException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
@RestController
public class SanctionsController {

    private SanctionsService sanctionsService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/sanctions")
    public void createSanctions(@RequestBody @Valid SanctionsDto sanctionsDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new RequestErrorException(bindingResult);
        }sanctionsService.createSanctions(sanctionsDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sanctions/page")
    public Page<SanctionsDto> fetchSanctionsList(Pageable pageable) {
        return sanctionsService.fetchSanctionsList(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/sanctions")
    public void deleteSanctions(@RequestParam("sanctionsKey") String sanctionsKey) {
        sanctionsService.deleteSanctions(sanctionsKey);
    }
}
