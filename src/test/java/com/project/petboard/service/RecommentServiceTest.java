package com.project.petboard.service;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.comment.CommentResponseDto;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.recomment.*;
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

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RecommentServiceTest {

    RecommentRepository recommentRepository = mock(RecommentRepository.class);

    MemberRepository memberRepository = mock(MemberRepository.class);

    PostRepository postRepository = mock(PostRepository.class);

    CommentRepository commentRepository = mock(CommentRepository.class);

    RecommentService recommentService = new RecommentService(recommentRepository,memberRepository,postRepository,commentRepository);

    private RecommentRequestDto recommentRequestDto;

    private Recomment recomment;

    @BeforeEach
    public void setup(){
       recomment = Recomment.builder()
               .comment(new Comment())
               .member(new Member())
               .post(new Post())
               .recommentContents("contents")
               .build();
       recommentRequestDto = new RecommentRequestDto();
    }

    @Test
    @DisplayName("리댓글 생성 테스트")
    public void createRecommentShouldBeSuccess(){
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(new Member());
        given(postRepository.findByPostNumber(1L)).willReturn(new Post());
        given(commentRepository.findByCommentNumber(1L)).willReturn(new Comment());
        given(recommentRepository.save(any())).willReturn(recomment);
        //when
        ReocommentResponseDto reocommentResponseDto = recommentService.createRecomment(recommentRequestDto);
        //then
        assertThat(reocommentResponseDto).isNotNull();
    }

    @Test
    @DisplayName("리댓글 생성 실패 테스트")
    public void createReommentShouldBeFail(){
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(new Member());
        given(postRepository.findByPostNumber(1L)).willReturn(new Post());
        given(commentRepository.findByCommentNumber(1L)).willReturn(new Comment());
        given(recommentRepository.save(any())).willReturn(null);
        try {
            //when
            ReocommentResponseDto reocommentResponseDto = recommentService.createRecomment(recommentRequestDto);
        }catch (CustomErrorException e){
            //then
            assertThat(e.getErrorCode()).isEqualTo(HttpErrorCode.BAD_REQUEST);
        }
    }

    @Test
    @DisplayName("리댓글 삭제 테스트")
    public void deleteRecommentShouldBeSuccess(){
        //when
        recommentRepository.deleteById(1L);
        //then
        verify(recommentRepository).deleteById(1L);
    }

    @DisplayName("리댓글 페이지 가져오기 테스트")
    @Test
    public void getCommentPageTestShouldBeSuccess() {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        List<Recomment> recommentList = new ArrayList<>();
        recommentList.add(recomment);
        recommentList.add(recomment);
        Page<Recomment> recomments = new PageImpl<>(recommentList);
        given(recommentRepository.findAll(pageable)).willReturn(recomments);
        //when
        Page<ReocommentResponseDto> requestRecommentPage = recommentService.requestRecommentPage(pageable);
        //then
        assertThat(requestRecommentPage.getSize()).isEqualTo(2);
    }

}
