package com.didenko.http.controller;

import com.didenko.dto.UserLoginDto;
import com.didenko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    @GetMapping
    public String loginPage(){
        return "/login";
    }


}
