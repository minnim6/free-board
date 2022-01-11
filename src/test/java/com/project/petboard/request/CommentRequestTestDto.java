package com.project.petboard.request;

import lombok.Getter;

@Getter
public class CommentRequestTestDto {
    Long memberNumber = 1L;
    Long postNumber = 1L;
    String commentContents = "내용내용내내내";
}
