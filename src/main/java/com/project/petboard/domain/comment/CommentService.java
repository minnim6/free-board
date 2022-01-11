package com.project.petboard.domain.comment;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.page.RequestPage;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.recomment.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final RecommentRepository recommentRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
            return new CommentResponseDto(commentRepository.save(commentRequestDto.toEntity(
                    getPostEntity(commentRequestDto.getPostNumber()),getMemberEntity(commentRequestDto.getMemberNumber())
            )));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentPage(RequestPage requestPage) {
            Pageable pageable = PageRequest.of(requestPage.getPage(),requestPage.getSize());
            List<CommentResponseDto> page = commentRepository.findAll(pageable).getContent().stream()
                    .map(comment -> new CommentResponseDto(comment))
                    .collect(Collectors.toList());
            return page;
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
