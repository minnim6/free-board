package com.project.petboard.service;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.recomment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        RecommentResponseDto reocommentResponseDto = recommentService.createRecomment(recommentRequestDto);
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
            RecommentResponseDto recommentResponseDto = recommentService.createRecomment(recommentRequestDto);
        }catch (NullPointerException e){
            //then
            assertThat(recommentRepository.findByRecommentNumber(1L)).isNull();
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
        List<Recomment> recommentList = new ArrayList<>();
        recommentList.add(recomment);
        recommentList.add(recomment);
        given(recommentRepository.findAllByComment(any())).willReturn(recommentList);
        //when
        List<RecommentResponseDto> requestRecommentPage = recommentService.requestRecommentPage(1L);
        //then
        assertThat(requestRecommentPage.size()).isEqualTo(2);
    }

}
