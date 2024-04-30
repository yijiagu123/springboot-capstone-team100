package com.team100.springboot.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class SubscriptionController {

    @GetMapping("/goToSubscription")
    public String goToSubscription() {
        return "subscription";
    }
}
