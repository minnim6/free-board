package com.project.petboard.domain.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Page<Post> requestPageBoard(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public void createPostBoard(PostDto postDto){
        postRepository.save(postDto.toEntity());
    }

}
