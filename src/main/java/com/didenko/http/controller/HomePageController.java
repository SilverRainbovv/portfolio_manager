package com.didenko.http.controller;

import com.didenko.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class HomePageController {

    @GetMapping
    public String homePage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("authenticated", user != null);
        return "/homePage";
    }

}
