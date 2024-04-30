package com.team100.springboot.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PaymentController {

    @GetMapping("/goToPayment")
    public String goToPayment() {
        return "payment";
    }
}
