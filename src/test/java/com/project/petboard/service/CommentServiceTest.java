package com.project.petboard.service;

import com.project.petboard.domain.comment.*;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.recomment.RecommentRepository;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.HttpErrorCode;
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

public class CommentServiceTest {

    CommentRepository commentRepository = mock(CommentRepository.class);

    MemberRepository memberRepository = mock(MemberRepository.class);

    PostRepository postRepository = mock(PostRepository.class);

    RecommentRepository recommentRepository = mock(RecommentRepository.class);

    CommentService commentService = new CommentService(commentRepository, memberRepository, postRepository,recommentRepository);

    private CommentRequestDto commentRequestDto;

    private Comment comment;

    @BeforeEach
    public void setup() {
        comment = Comment.builder()
                .post(new Post())
                .member(new Member())
                .commentContents("contents")
                .build();
        commentRequestDto = new CommentRequestDto();
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    public void createCommentTestShouldBeSuccess() {
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(new Member());
        given(postRepository.findByPostNumber(1L)).willReturn(new Post());
        given(commentRepository.save(any())).willReturn(comment);
        //when
        CommentResponseDto commentResponseDto = commentService.createComment(commentRequestDto);
        //then
        assertThat(commentResponseDto).isNotNull();
    }

    @Test
    @DisplayName("댓글 생성 실패 테스트")
    public void createCommentTestShouldBeFail() {
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(new Member());
        given(postRepository.findByPostNumber(1L)).willReturn(new Post());
        given(commentRepository.save(any())).willReturn(null);
        try {
            //when
            CommentResponseDto commentResponseDto = commentService.createComment(commentRequestDto);
        } catch (CustomErrorException e) {
            //then
            assertThat(e.getErrorCode()).isEqualTo(HttpErrorCode.BAD_REQUEST);
        }
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void deleteCommentTestShouldBeFail() {
        //given
        given(commentRepository.findByCommentNumber(1L)).willReturn(comment);
        //when
        commentService.deleteComment(1L);
        //then
        verify(commentRepository).deleteById(1L);
        verify(recommentRepository).deleteAllByComment(comment);
    }

    @DisplayName("페이지 가져오기 테스트")
    @Test
    public void getCommentPageTestShouldBeSuccess() {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        commentList.add(comment);
        Page<Comment> comments = new PageImpl<>(commentList);
        given(commentRepository.findAll(pageable)).willReturn(comments);
        //when
        Page<CommentResponseDto> commentResponseDtos = commentService.getCommentPage(pageable);
        //then
        assertThat(commentResponseDtos.getSize()).isEqualTo(2);
    }
}