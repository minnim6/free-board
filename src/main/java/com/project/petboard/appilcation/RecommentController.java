package com.project.petboard.appilcation;

import com.project.petboard.domain.recomment.Recomment;
import com.project.petboard.domain.recomment.RecommentRequestDto;
import com.project.petboard.domain.recomment.RecommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class RecommentController {
    private final RecommentService recommentService;

    @PostMapping(value = "/recomment")
    public void createRecomment(@RequestBody RecommentRequestDto recommentDto){
        recommentService.createRecomment(recommentDto);
    }
    @DeleteMapping(value = "/recomment")
    public void deleteRecomment(@RequestParam("recommentNumber")Long recommentNumber){
        recommentService.deleteRecomment(recommentNumber);
    }

    @GetMapping(value = "/recomment/page")
    public Page<Recomment> requestRecommentPage(Pageable pageable){
        return recommentService.requestRecommentPage(pageable);
    }
}
