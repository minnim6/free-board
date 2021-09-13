package com.project.petboard.domain.comment;


import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.dummy.PostDummy;
import com.project.petboard.dummy.CommentDummy;
import com.project.petboard.dummy.MemberDummy;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
//@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    MemberDummy memberDummy = new MemberDummy();

    PostDummy postDummy;

    CommentDummy commentDummy;

    @BeforeEach
    public void setup(){
        memberRepository.save(memberDummy.toEntity());

        Member member = memberRepository.findAll().get(0);

        postDummy = new PostDummy(member);
        postRepository.save(postDummy.toEntity());

        Post post = postRepository.findAll().get(0);

        commentDummy = new CommentDummy(post,member);
        commentRepository.save(commentDummy.toEntity());
    }

    @AfterEach
    public void cleanup(){
        commentRepository.deleteAll();;
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    public void 댓글_조회(){
        Comment comment = commentRepository.findAll().get(0);

        assertThat(comment.getCommentContents()).isEqualTo(commentDummy.getCommentContents());
    }

    @Test
    public void 댓글_수정(){
        String updateCommentContents = "내용수정";

        Comment comment = commentRepository.findAll().get(0);

        comment.update(updateCommentContents);
        commentRepository.save(comment);

        assertThat(commentRepository.findAll().get(0).getCommentContents()).isEqualTo(updateCommentContents);
    }

    @Test
    public void 댓글_삭제(){
        Comment comment = commentRepository.findAll().get(0);
        Long commentNumber = comment.getCommentNumber();

        commentRepository.delete(comment);

        assertThrows(IndexOutOfBoundsException.class,()-> commentRepository.findAll().get(0));
    }

}
