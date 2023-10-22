package com.didenko.http.controller;

import com.didenko.dto.UserCreateEditDto;
import com.didenko.entity.User;
import com.didenko.service.PortfolioService;
import com.didenko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;

    @GetMapping
    public String userRedirect(@AuthenticationPrincipal User user){

        return "redirect:/user/" + user.getId();
    }


    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        validateUser(id, user.getId());

        var portfolios = portfolioService.getByUserId(id).stream().toList();

        return userService.findById(id)
                .map(maybeUser -> {
                    model.addAttribute("user", maybeUser);
                    model.addAttribute("portfolios", portfolios);
                    return "user/userPage";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void validateUser(Long urlId, Long authenticatedId) {
        if (!urlId.equals(authenticatedId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public void edit(@ModelAttribute UserCreateEditDto user){

        System.out.println();
    }
}
