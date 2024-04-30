package com.team100.springboot.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PaymentController {

    @GetMapping("/goToSubscription")
    public String goToSubscription() {
        return "payment";
    }
}
