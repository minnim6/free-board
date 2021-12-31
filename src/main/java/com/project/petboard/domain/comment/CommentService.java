package com.project.petboard.domain.comment;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
       return new CommentResponseDto(commentRepository.save(commentRequestDto.toEntity(
                getPostEntity(commentRequestDto.getPostNumber()),getMemberEntity(commentRequestDto.getMemberNumber())
        )));
    }

    @Transactional(readOnly = true)
    public Page<Comment> fetchCommentPage(Pageable pageable) {
        return commentRepository.findAll(pageable);
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
