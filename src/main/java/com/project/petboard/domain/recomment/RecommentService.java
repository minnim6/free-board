package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class RecommentService {

    private final RecommentRepository recommentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    public RecommentResponseDto createRecomment(RecommentRequestDto recommentRequestDto) {
            return new RecommentResponseDto(recommentRepository.save(recommentRequestDto.toEntity(getPostEntity(recommentRequestDto.getPostNumber())
                    ,getMemberEntity(recommentRequestDto.getMemberNumber()),getCommentEntity(recommentRequestDto.getCommentNumber()))));
    }

    public void deleteRecomment(Long recommentNumber) {
        recommentRepository.deleteById(recommentNumber);
    }

    @Transactional(readOnly = true)
    public List<RecommentResponseDto> requestRecommentPage(Long commentNumber) {
            List<RecommentResponseDto> page = recommentRepository.findAllByComment(getCommentEntity(commentNumber)).stream()
                    .map(recomment -> new RecommentResponseDto(recomment))
                    .collect(Collectors.toList());
            return page;
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
