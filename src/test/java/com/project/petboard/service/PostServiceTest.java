package com.project.petboard.service;

import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.page.RequestPage;
import com.project.petboard.domain.post.*;
import com.project.petboard.domain.recomment.RecommentRepository;
import com.project.petboard.domain.report.ReportRepository;
import com.project.petboard.domain.report.Sanctions;
import com.project.petboard.domain.report.SanctionsRepository;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.ReportErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PostServiceTest {

    PostRepository postRepository = mock(PostRepository.class);

    ReportRepository reportRepository = mock(ReportRepository.class);

    MemberRepository memberRepository = mock(MemberRepository.class);

    SanctionsRepository sanctionsRepository = mock(SanctionsRepository.class);

    CommentRepository commentRepository = mock(CommentRepository.class);

    RecommentRepository recommentRepository = mock(RecommentRepository.class);

    PostService postService = new PostService(postRepository, reportRepository, memberRepository, sanctionsRepository,
            commentRepository,recommentRepository);

    private PostRequestDto postRequestDto;

    private Post post;

    @BeforeEach
    public void setup() {
        post = Post.builder()
                .member(new Member())
                .postTitle("title")
                .postContents("contents")
                .postCategory("category")
                .build();
        postRequestDto = new PostRequestDto();
    }

    @Test
    @DisplayName("????????? ?????? ?????????")
    public void createPostTestShouldBeSuccess() {
        //given
        given(postRepository.save(any())).willReturn(post);
        //when
        PostResponseDto postResponseDto = postService.savePost(postRequestDto);
        //then
        assertThat(post.getPostNumber()).isEqualTo(postResponseDto.getPostNumber());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????????")
    public void createPostTestShouldBeFail() {
        //given
        given(postRepository.save(any())).willReturn(null);
        try {
            //when
            PostResponseDto postResponseDto= postService.savePost(null);
        }catch (NullPointerException e) {
            //then
            assertThat(postRepository.findByPostNumber(1L)).isNull();
        }
    }

    @Test
    @DisplayName("????????? ?????? ?????????")
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
    @DisplayName("????????? ?????? ?????? ?????????")
    public void findPostTestShouldBeFail() {
        //given
        PostResponseDto postResponseDto;
        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findByPostNumber(1L)).willReturn(null);
        try {
            //when
            postResponseDto = postService.fetchPost(1L);
        }catch (NullPointerException e){
            //then
            assertThat(postRepository.findByPostNumber(1L)).isNull();
        }
    }

    @Test
    @DisplayName("????????? ?????? ?????????")
    public void deletePostShouldBeSuccess() {
        //given
        given(postRepository.findByPostNumber(1L)).willReturn(post);
        //when
        postService.deletePost(1L);
        //then
        verify(postRepository).deleteById(1L);
        verify(recommentRepository).deleteAllByPost(post);
        verify(commentRepository).deleteAllByPost(post);
        verify(reportRepository).deleteAllByPost(post);
    }


    @Test
    @DisplayName("????????? ?????? ?????????")
    public void reportPostShouldBeSuccess() {
        //given
        Member member = new Member();
        Sanctions sanctions = new Sanctions("?????????", 1, "?????? ??????");
        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findByPostNumber(1L)).willReturn(post);
        given(memberRepository.findByMemberNumber(1L)).willReturn(member);
        given(reportRepository.existsByMemberAndPost(member, post)).willReturn(false);
        given(sanctionsRepository.findBySanctionsKey("?????????")).willReturn(sanctions);
        //when
        postService.reportPost(1L, 1L);
        //then
        Post findPost = postRepository.findByPostNumber(1L);
        assertThat(findPost.getPostReportCount()).isEqualTo(1);
        assertThat(findPost.getPostStatus()).isEqualTo(PostStatus.N);
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????????")
    public void reportPostShouldBeFail() {
        //given
        Member member = new Member();
        Sanctions sanctions = new Sanctions("?????????", 1, "?????? ??????");
        given(postRepository.save(any())).willReturn(post);
        given(postRepository.findByPostNumber(1L)).willReturn(post);
        given(memberRepository.findByMemberNumber(1L)).willReturn(member);
        given(reportRepository.existsByMemberAndPost(member, post)).willReturn(true);
        given(sanctionsRepository.findBySanctionsKey("?????????")).willReturn(sanctions);
       try {
           //when
           postService.reportPost(1L, 1L);
       }catch (CustomErrorException e) {
           //then
           assertThat(e.getErrorCode()).isEqualTo(ReportErrorCode.REPORT_DUPLICATION);
       }
    }

    @DisplayName("????????? ???????????? ?????????")
    @Test
    public void getPostPageTestShouldBeSuccess() {
        //given
        Pageable pageable = PageRequest.of(0,10);
        List<Post> postList = new ArrayList<>();
        postList.add(post);
        postList.add(post);
        Page<Post> posts = new PageImpl<>(postList);
        RequestPage requestPage = new RequestPage(0,10);
        given(postRepository.findAllByPostStatus(pageable,PostStatus.Y)).willReturn(posts);
        //when
        List<PostResponseDto> postResponseDtos = postService.getPostPage(requestPage);
        //then
        assertThat(postResponseDtos.size()).isEqualTo(2);
    }

}
