package com.project.petboard.appilcation;

import com.project.petboard.domain.report.Sanctions;
import com.project.petboard.domain.report.SanctionsDto;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.domain.report.SanctionsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class SanctionsController {

    private SanctionsService sanctionsService;

    @PostMapping
    public void createSanctions(SanctionsDto sanctionsDto){
        sanctionsService.createSanctions(sanctionsDto);
    }

    public List<Sanctions> fetchSanctionsList(){
        return sanctionsService.fetchSanctionsList();
    }

    public void deleteSanctions(String sanctionsKey){
        sanctionsService.deleteSanctions(sanctionsKey);
    }
}
