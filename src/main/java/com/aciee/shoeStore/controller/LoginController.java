package com.aciee.shoeStore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("")
    public String login() {
        return "login";
    }

    @GetMapping("/logoff")
    public String processLogoff() {
        return "redirect:/login";
    }
}
