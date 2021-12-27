package com.project.petboard.domain.recomment;

import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RecommentService {

    private final RecommentRepository recommentRepository;

    public void createRecomment(RecommentDto recommentDto) {
        recommentRepository.save(recommentDto.toEntity());
    }

    public void deleteRecomment(Long recommentNumber) {
        recommentRepository.deleteById(recommentNumber);
    }

    @Transactional(readOnly = true)
    public Page<Recomment> requestRecommentPage(Pageable pageable) {
        try {
            return recommentRepository.findAll(pageable);
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(), ErrorCode.NOT_FOUND);
        }
    }

}
