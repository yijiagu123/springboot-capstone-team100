package com.team100.springboot.controller;

import com.team100.springboot.dto.PostDto;
import com.team100.springboot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/submit-post")
    public ModelAndView submitPost(@RequestParam("postTitle") String title,
                                   @RequestParam("postBody") String content,
                                   @RequestParam("postImage") MultipartFile file) {
        PostDto postDto = new PostDto(title, content);

        postService.savePost(postDto);

        return new ModelAndView("redirect:/home");
    }
}
