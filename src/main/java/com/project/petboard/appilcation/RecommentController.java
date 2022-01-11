package com.project.petboard.appilcation;

import com.project.petboard.domain.recomment.RecommentRequestDto;
import com.project.petboard.domain.recomment.RecommentResponseDto;
import com.project.petboard.domain.recomment.RecommentService;
import com.project.petboard.infrastructure.exception.RequestErrorException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class RecommentController {

    private final RecommentService recommentService;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping(value = "/recomment")
    public void createRecomment(@RequestBody @Valid RecommentRequestDto recommentDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RequestErrorException(bindingResult);
        }recommentService.createRecomment(recommentDto);
    }
    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping(value = "/recomment")
    public void deleteRecomment(@RequestParam("recommentNumber")Long recommentNumber){
        recommentService.deleteRecomment(recommentNumber);
    }

    @GetMapping(value = "/recomment/page")
    public List<RecommentResponseDto> requestRecommentPage(@RequestParam("commentNumber")Long commentNumber){
        return recommentService.requestRecommentPage(commentNumber);
    }
}
