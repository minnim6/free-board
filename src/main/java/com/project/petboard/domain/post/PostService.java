package com.project.petboard.domain.post;

import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.recomment.RecommentRepository;
import com.project.petboard.domain.report.ReportRepository;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import com.project.petboard.infrastructure.exception.ReportErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;

    private final SanctionsRepository sanctionsRepository;

    private final CommentRepository commentRepository;

    private final RecommentRepository recommentRepository;

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostPage(Pageable pageable) {
        try {
            return postRepository.findAllByPostStatus(pageable,PostStatus.Y).map(PostResponseDto::new);
        } catch (Exception e) {
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.NOT_FOUND);
        }
    }

    public Long createPost(PostRequestDto postRequestDto) {
        try {
            return postRepository.save(postRequestDto.toEntity(getMemberEntity(postRequestDto.getMemberNumber()))).getPostNumber();
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.BAD_REQUEST);
        }
    }

    public void deletePost(Long postNumber) {
        Post post = getPostEntity(postNumber);
        reportRepository.deleteAllByPost(post);
        recommentRepository.deleteAllByPost(post);
        commentRepository.deleteAllByPost(post);
        postRepository.deleteById(postNumber);
    }

    public void visibleChangePostStatus(Long postNumber) {
        Post post = getPostEntity(postNumber);
        post.toggleStatusY();
    }

    @Transactional(readOnly = true)
    public PostResponseDto fetchPost(Long postNumber) {
        try {
            Post post = postRepository.findByPostNumber(postNumber);
            if(post.getPostStatus().equals(PostStatus.N)) {
                throw new CustomErrorException(HttpErrorCode.NOT_FOUND);
            }
            return new PostResponseDto(post);
        } catch (Exception e) {
            throw new CustomErrorException(e.getMessage(), HttpErrorCode.NOT_FOUND);
        }
    }

    public void reportPost(Long memberNumber, Long postNumber) {
        if (isCheckOverlapReport(memberNumber, postNumber)) {
           throw new CustomErrorException(ReportErrorCode.REPORT_DUPLICATION);
        }
        addReport(postNumber);
        if (isSanctionsCountOverCheck(postNumber)) {
            blindPost(postNumber);
        }
    }

    private boolean isSanctionsCountOverCheck(Long postNumber) {
        return getPostEntity(postNumber).getPostReportCount() >= getSanctionsValue();
    }

    private void blindPost(Long postNumber) {
        Post post = getPostEntity(postNumber);
        post.toggleStatusN();
    }

    private int getSanctionsValue() {
        return sanctionsRepository.findBySanctionsKey("게시물")
                .getSanctionsValue();
    }

    private void addReport(Long postNumber) {
        Post post = getPostEntity(postNumber);
        post.addReportCount();
    }

    private Member getMemberEntity(Long memberNumber) {
        return memberRepository.findByMemberNumber(memberNumber);
    }

    private Post getPostEntity(Long postNumber) {
        return postRepository.findByPostNumber(postNumber);
    }

    @Transactional(readOnly = true)
    public boolean isCheckOverlapReport(Long memberNumber, Long postNumber) {
        return reportRepository.existsByMemberAndPost(getMemberEntity(memberNumber), getPostEntity(postNumber));
    }

}
