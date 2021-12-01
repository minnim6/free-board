package com.project.petboard.domain.recomment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecommentService {

    private final RecommentRepository recommentRepository;

    public void createRecomment(RecommentDto recommentDto){
        recommentRepository.save(recommentDto.toEntity());
    }
    public void deleteRecomment(Long recommentNumber){
        recommentRepository.deleteById(recommentNumber);
    }
    public Page<Recomment> requestRecommentPage(Pageable pageable){
        return recommentRepository.findAll(pageable);
    }

}
