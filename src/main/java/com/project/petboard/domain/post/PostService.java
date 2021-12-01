package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.report.ReportRepository;
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

    public Page<Post> requestPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public void createPost(PostDto postDto) {
        postRepository.save(postDto.toEntity());
    }

    public void deletePost(Long postNumber) {
        postRepository.deleteById(postNumber);
    }

    @Transactional(readOnly = true)
    public PostDto fetchPost(Long postNumber) {
        return new PostDto(postRepository.findByPostNumber(postNumber));
    }

    public void reportPost(Long memberId,Long postNumber){

    }

    public boolean isCheckOverlapReport(Member member,Post post){
        return reportRepository.existsByMemberAndPost(member,post);
    }

}
