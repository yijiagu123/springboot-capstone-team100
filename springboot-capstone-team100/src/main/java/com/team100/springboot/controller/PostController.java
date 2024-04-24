package com.team100.springboot.controller;

import com.team100.springboot.dto.PostDto;
import com.team100.springboot.entity.Post;
import com.team100.springboot.entity.User;
import com.team100.springboot.service.PostService;
import com.team100.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @Autowired
    private UserService userService;

    @PostMapping("/submit-post")
    public ModelAndView submitPost(@RequestParam("postTitle") String title,
                                   @RequestParam("postBody") String content,
                                   Principal principal) {
        PostDto postDto = new PostDto(title, content);
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        postService.savePost(postDto,user);
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/listPage")
    public String viewPosts(Model model, @RequestParam(defaultValue = "0") int page,
                            @RequestParam(name = "search", required = false) String search) {
        Pageable pageable = PageRequest.of(page, 10000); // 10为每页显示的数量

        Page<Post> postPage;

        if (search != null && !search.isEmpty()) {
            postPage = postService.findAllPostsByKeyword(search, pageable);
        } else {
            postPage = postService.findAllPosts(pageable);
        }

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("search", search);

        return "home";
    }


    // delete post controller
    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return "redirect:/home";
    }


    // update post controller
    @PostMapping("/update")
    public ModelAndView updatePost(@RequestParam("postId") Long postId,@RequestParam("title") String title,
                                   @RequestParam("content") String content,
                                   Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Post updatedPost=new Post(){{
            setPostId(postId);setTitle(title);setContent(content);setPostAt(LocalDateTime.now());setUser(user);
        }};
        postService.updatePost(updatedPost);
        return new ModelAndView("redirect:/home");
    }

}
