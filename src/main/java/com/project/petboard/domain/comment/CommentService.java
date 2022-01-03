package com.project.petboard.domain.comment;

import com.fasterxml.jackson.databind.util.Converter;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.infrastructure.exception.CrudErrorCode;
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

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        try {
            return new CommentResponseDto(commentRepository.save(commentRequestDto.toEntity(
                    getPostEntity(commentRequestDto.getPostNumber()),getMemberEntity(commentRequestDto.getMemberNumber())
            )));
        }catch (NullPointerException e){
            throw new CustomErrorException(e.getMessage(), CrudErrorCode.NULL_EXCEPTION);
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> fetchCommentPage(Pageable pageable) {
        return commentRepository.findAll(pageable).map(new Function<Comment, CommentResponseDto>() {
            @Override
            public CommentResponseDto apply(Comment comment) {
                // Conversion logic
                return new CommentResponseDto(comment);
            }
        });
    }

    public void deleteComment(Long commentNumber) {
        commentRepository.deleteById(commentNumber);
    }

    private Member getMemberEntity(Long memberNumber){
        return memberRepository.findByMemberNumber(memberNumber);
    }

    private Post getPostEntity(Long postNumber){
        return postRepository.findByPostNumber(postNumber);
    }

}
