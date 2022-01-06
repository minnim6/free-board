package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@RequiredArgsConstructor
@Transactional
@Service
public class RecommentService {

    private final RecommentRepository recommentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public ReocommentResponseDto createRecomment(RecommentRequestDto recommentRequestDto) {
        try {
            return new ReocommentResponseDto(recommentRepository.save(recommentRequestDto.toEntity(getPostEntity(recommentRequestDto.getPostNumber())
                    ,getMemberEntity(recommentRequestDto.getMemberNumber()),getCommentEntity(recommentRequestDto.getCommentNumber()))));
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.BAD_REQUEST);
        }
    }

    public void deleteRecomment(Long recommentNumber) {
        recommentRepository.deleteById(recommentNumber);
    }

    @Transactional(readOnly = true)
    public Page<ReocommentResponseDto> requestRecommentPage(Pageable pageable) {
        try {
            return recommentRepository.findAll(pageable).map(new Function<Recomment, ReocommentResponseDto>() {
                @Override
                public ReocommentResponseDto apply(Recomment recomment) {
                    // Conversion logic
                    return new ReocommentResponseDto(recomment);
                }
            });
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.NOT_FOUND);
        }
    }

    private Comment getCommentEntity(Long commentNumber){
        return commentRepository.findByCommentNumber(commentNumber);
    }

    private Member getMemberEntity(Long memeberNumber){
        return memberRepository.findByMemberNumber(memeberNumber);
    }

    private Post getPostEntity(Long postNumber){
        return postRepository.findByPostNumber(postNumber);
    }

}
