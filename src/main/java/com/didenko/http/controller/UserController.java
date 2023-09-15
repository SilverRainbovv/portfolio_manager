package com.didenko.http.controller;

import com.didenko.service.PortfolioService;
import com.didenko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;

//    @GetMapping
//    public String findAll(Model model){
//        model.addAttribute("users", userService.findAll());
//        return "users";
//    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long id) {
        var portfolios = portfolioService.getByUserId(id).stream();/*.limit(4);*/

        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("portfolios", portfolios);
                    return "user/userPage";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}