package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReportRepository reportRepository;

    private int sanctionsStandard;

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

        Report report = reportDto.toEntity();
        Member member = report.getMember();
        Post post = report.getPost();

        if (isDuplicateReportCheck(member,post)){
            reportRepository.save(reportDto.toEntity());
        }

        if(fetchReportCount(post)>sanctionsStandard){
            post.toggleStatusN();
            postRepository.save(post);
        }

    }

    public int fetchReportCount(Post post){
        return reportRepository.countByPost(post);
    }

    public Boolean isDuplicateReportCheck(Member member,Post post) {
        return  reportRepository.countByPostAndMember(member,post);
    }

}
