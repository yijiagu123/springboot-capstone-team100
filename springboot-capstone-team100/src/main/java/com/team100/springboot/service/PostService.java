package com.team100.springboot.service;

import com.team100.springboot.dto.PostDto;
import com.team100.springboot.entity.Post;
import com.team100.springboot.entity.User;
import com.team100.springboot.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void savePost(PostDto postDto, User user) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPostAt(LocalDateTime.now());
        post.setUser(user);
        postRepository.save(post);
    }

    public Page<Post> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> findAllPostsByKeyword(String search, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(search,search,pageable);
    }

    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }


    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public void updatePost(Post updatedPost) {
        Long postId = updatedPost.getPostId();
        // get original post object
        Post existingPost = postRepository.findById(postId).orElse(null);

        if (existingPost != null) {
            // update post
            BeanUtils.copyProperties(updatedPost, existingPost, "id");
            // save updated post
            postRepository.save(existingPost);
        } else {
            // dealing with post not found exception
            throw new IllegalArgumentException("Post with ID " + postId + " not found.");
        }
    }

}
