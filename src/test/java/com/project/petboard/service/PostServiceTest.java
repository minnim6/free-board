package com.project.petboard.service;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.*;
import com.project.petboard.domain.report.ReportRepository;
import com.project.petboard.domain.report.Sanctions;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
import com.project.petboard.infrastructure.exception.ReportErrorCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    PostRepository postRepository = mock(PostRepository.class);

    ReportRepository reportRepository = mock(ReportRepository.class);

    MemberRepository memberRepository = mock(MemberRepository.class);

    SanctionsRepository sanctionsRepository = mock(SanctionsRepository.class);

    PostService postService = new PostService(postRepository, reportRepository, memberRepository, sanctionsRepository);

    private PostRequestDto postRequestDto;

    private Post post;

    @Before
    public void setup() {
        postRequestDto = new PostRequestDto(
                1L,"title","contnents","Category"
        );
        post = postRequestDto.toEntity(new Member());
    }

    @Test
    @DisplayName("게시물 생성 테스트")
    public void createPostTestShouldBeSuccess() {
        //given
        given(postRepository.save(any())).willReturn(post);
        //when
        Long postNumber = postService.createPost(postRequestDto);
        //then
        assertThat(post.getPostNumber()).isEqualTo(postNumber);
    }

    @Test
    @DisplayName("게시물 생성 실패 테스트")
    public void createPostTestShouldBeFail() {
        //given
        given(postRepository.save(any())).willReturn(null);
        try {
            //when
            Long postNumber = postService.createPost(null);
        }catch (CustomErrorException e) {
            //then
            assertThat(e.getErrorCode()).isEqualTo(HttpErrorCode.BAD_REQUEST);
        }
    }

    @Test
    @DisplayName("게시물 조회 테스트")
    public void findPostTestShouldBeSuccess() {
        //given
        PostResponseDto postResponseDto;
        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findByPostNumber(1L)).willReturn(post);
        //when
        postResponseDto = postService.fetchPost(1L);
        //then
        assertThat(postResponseDto).isNotNull();
    }

    @Test
    @DisplayName("게시물 조회 실패 테스트")
    public void findPostTestShouldBeFail() {
        //given
        PostResponseDto postResponseDto;
        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findByPostNumber(1L)).willReturn(null);
        try {
            //when
            postResponseDto = postService.fetchPost(1L);
        }catch (CustomErrorException e){
            //then
            assertThat(e.getErrorCode()).isEqualTo(HttpErrorCode.NOT_FOUND);
        }
    }

    @Test
    @DisplayName("게시물 삭제 테스트")
    public void deletePostShouldBeSuccess() {
        //when
        postService.deletePost(1L);
        //then
        verify(postRepository).deleteById(1L);
    }


    @Test
    @DisplayName("게시물 신고 테스트")
    public void reportPostShouldBeSuccess() {
        //given
        Member member = new Member();
        Sanctions sanctions = new Sanctions("게시물", 1, "세부 규정");
        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findByPostNumber(1L)).willReturn(post);
        given(memberRepository.findByMemberNumber(1L)).willReturn(member);
        given(reportRepository.existsByMemberAndPost(member, post)).willReturn(false);
        given(sanctionsRepository.findBySanctionsKey("게시물")).willReturn(sanctions);
        //when
        postService.reportPost(1L, 1L);
        //then
        Post findPost = postRepository.findByPostNumber(1L);
        assertThat(findPost.getPostReportCount()).isEqualTo(1);
        assertThat(findPost.getPostStatus()).isEqualTo(PostStatus.N);
    }

    @Test
    @DisplayName("게시물 신고 실패 테스트")
    public void reportPostShouldBeFail() {
        //given
        Member member = new Member();
        Sanctions sanctions = new Sanctions("게시물", 1, "세부 규정");
        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findByPostNumber(1L)).willReturn(post);
        given(memberRepository.findByMemberNumber(1L)).willReturn(member);
        given(reportRepository.existsByMemberAndPost(member, post)).willReturn(true);
        given(sanctionsRepository.findBySanctionsKey("게시물")).willReturn(sanctions);
       try {
           //when
           postService.reportPost(1L, 1L);
       }catch (CustomErrorException e) {
           //then
           assertThat(e.getErrorCode()).isEqualTo(ReportErrorCode.REPORT_DUPLICATION);
       }
    }
}
