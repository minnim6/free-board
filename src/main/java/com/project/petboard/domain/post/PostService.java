package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.report.ReportRepository;
import com.project.petboard.domain.report.SanctionsRepository;
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
public class PostService {

    private final PostRepository postRepository;

    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;

    private final SanctionsRepository sanctionsRepository;

    @Transactional(readOnly = true)
    public Page<Post> requestPage(Pageable pageable) {
        try {
            return postRepository.findAll(pageable);
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(), ErrorCode.NOT_FOUND);
        }
    }

    public void createPost(PostDto postDto) {
        postRepository.save(postDto.toEntity());
    }

    public void deletePost(Long postNumber) {
        postRepository.deleteById(postNumber);
    }

    public void visibleChangePostStatus(Long postNumber) {
        Post post = getPostEntity(postNumber);
        post.toggleStatusY();
    }

    @Transactional(readOnly = true)
    public PostRequestDto fetchPost(Long postNumber) {
        try {
            return new PostRequestDto(postRepository.findByPostNumber(postNumber));
        }catch (Exception e){
            throw new CustomErrorException(e.getMessage(), ErrorCode.NOT_FOUND);
        }
    }

    public void reportPost(Long memberNumber,Long postNumber) {
      if (isCheckOverlapReport(memberNumber,postNumber)){
        //TODO:이미 신고했던 유저일경우 400에러던져줌
      }
      addReport(postNumber);
      if (isCheckOverlapReport(memberNumber,postNumber)){
          blindPost(postNumber);
      }
    }

    public boolean isSanctionsCountOverCheck(Long postNumber) {
        return getPostEntity(postNumber).getPostReportCount()>=getSanctionsValue();
    }

    public void blindPost(Long postNumber) {
        Post post = getPostEntity(postNumber);
        post.toggleStatusN();
    }

    public int getSanctionsValue() {
        return sanctionsRepository.findBySanctionsKey("게시물")
                .getSanctionsValue();
    }

    public void addReport(Long postNumber) {
        Post post = getPostEntity(postNumber);
        post.addReportCount();
    }

    public Member getMemberEntity(Long memberNumber) {
        return memberRepository.findByMemberNumber(memberNumber);
    }

    public Post getPostEntity(Long postNumber) {
        return postRepository.findByPostNumber(postNumber);
    }

    @Transactional(readOnly = true)
    public boolean isCheckOverlapReport(Long memberNumber,Long postNumber) {
        return reportRepository.existsByMemberAndPost(getMemberEntity(memberNumber),getPostEntity(postNumber));
    }

}
