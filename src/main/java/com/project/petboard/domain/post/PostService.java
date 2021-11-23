package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.report.ReportDto;
import com.project.petboard.domain.report.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
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

    public void reportPost(ReportDto reportDto) {

    }

  /*  public int fetchReportCount(Post post){
        return reportRepository.countByPost(post);
    }

    public Boolean isDuplicateReportCheck(Member member,Post post) {
        return  reportRepository.countByPostAndMember(member,post);
    }

   */

}
