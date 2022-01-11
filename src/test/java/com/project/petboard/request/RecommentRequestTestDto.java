package com.project.petboard.request;

import lombok.Getter;

@Getter
public class RecommentRequestTestDto {
    Long memberNumber = 1L;
    Long postNumber = 1L;
    Long commentNumber = 1L;
    String recommentContents = "내용내용내용내";
}
