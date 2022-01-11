package com.project.petboard.request;

import lombok.Getter;

@Getter
public class PostUpdateRequestTestDto {
    Long memberNumber = 2L;
    Long postNumber = 2L;
    String postTitle = "제목제목";
    String postContents = "내용내용내";
    String postCategory = "카테고리이이이";
}
