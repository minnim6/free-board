package com.project.petboard.domain.post;

public interface PostCustomRepository {
    PostDto findById(Long postNumber);
}
