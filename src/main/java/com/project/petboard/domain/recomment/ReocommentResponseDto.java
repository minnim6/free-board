package com.project.petboard.domain.recomment;

import com.project.petboard.domain.comment.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class ReocommentResponseDto {

    private final String memberNickname;

    private final Comment comment;

    private final String recommentContents;

    private final Date recommentCreateDate;

    public ReocommentResponseDto(Recomment recomment){
        this.memberNickname = recomment.getMember().getMemberNickname();
        this.comment = recomment.getComment();
        this.recommentContents = recomment.getRecommentContents();
        this.recommentCreateDate = recomment.getRecommentCreateDate();
    }

}
