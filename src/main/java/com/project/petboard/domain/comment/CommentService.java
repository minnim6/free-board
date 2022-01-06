package com.project.petboard.domain.comment;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.recomment.RecommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final RecommentRepository recommentRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        try {
            return new CommentResponseDto(commentRepository.save(commentRequestDto.toEntity(
                    getPostEntity(commentRequestDto.getPostNumber()),getMemberEntity(commentRequestDto.getMemberNumber())
            )));
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentPage(Pageable pageable) {
        return commentRepository.findAll(pageable).map(new Function<Comment, CommentResponseDto>() {
            @Override
            public CommentResponseDto apply(Comment comment) {
                return new CommentResponseDto(comment);
            }
        });
    }

    public void deleteComment(Long commentNumber) {
        recommentRepository.deleteAllByComment(getCommentEntity(commentNumber));
        commentRepository.deleteById(commentNumber);
    }

    private Comment getCommentEntity(Long commentNumber){
        return commentRepository.findByCommentNumber(commentNumber);
    }

    private Member getMemberEntity(Long memberNumber){
        return memberRepository.findByMemberNumber(memberNumber);
    }

    private Post getPostEntity(Long postNumber){
        return postRepository.findByPostNumber(postNumber);
    }

}
