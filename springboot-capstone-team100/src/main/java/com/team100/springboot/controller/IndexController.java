package com.team100.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // HTTP GET Request
    // http://localhost:8080
    @GetMapping("/")
    public String indexView(){
        return "index";
    }


}


