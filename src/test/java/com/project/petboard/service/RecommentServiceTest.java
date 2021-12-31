package com.project.petboard.service;

import com.project.petboard.domain.comment.Comment;
import com.project.petboard.domain.comment.CommentRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.recomment.*;
import com.project.petboard.infrastructure.exception.CrudErrorCode;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import lombok.Builder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

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

    @Before
    public void setup(){
        recommentRequestDto =
                new RecommentRequestDto(1L ,1L,
                        1L,"contnets");
        recomment = recommentRequestDto.toEntity(new Post(),new Member(),new Comment());
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
            assertThat(e.getErrorCode()).isEqualTo(CrudErrorCode.NULL_EXCEPTION);
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

}
