package com.team100.springboot.controller;

import jakarta.validation.Valid;
import com.team100.springboot.dto.UserDto;
import com.team100.springboot.entity.User;
import com.team100.springboot.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.security.Principal;
import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    @Autowired // Add Autowired annotation to inject UserService
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle home page request
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){

        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }


        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/userprofile")
    public String profile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        UserDto userDto = convertEntityToDto(user);
        model.addAttribute("user", userDto);
        return "userprofile";
    }

    // Method to convert User entity to UserDto
    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCompanyAddress(user.getBusinessAddress());
        userDto.setBusinessRegistrationNumber(user.getBusinessRegistrationNumber());
        return userDto;
    }


    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}



