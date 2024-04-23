package com.team100.springboot.service;

import com.team100.springboot.dto.PostDto;
import com.team100.springboot.entity.Post;
import com.team100.springboot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void savePost(PostDto postDto) {

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPostAt(LocalDateTime.now());
        
        postRepository.save(post);
    }
}
