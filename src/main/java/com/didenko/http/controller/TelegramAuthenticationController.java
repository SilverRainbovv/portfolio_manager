package com.didenko.http.controller;

import com.didenko.entity.User;
import com.didenko.service.TelegramAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("telegram")
@Controller
public class TelegramAuthenticationController {

    private final TelegramAuthenticationService authenticationService;

    @GetMapping("/authentication")
    public String authenticationInitialization(Model model, @AuthenticationPrincipal User user){

        var telegramUserInfo = authenticationService.createAuthenticationEntry(user);

        model.addAttribute("authenticationToken", telegramUserInfo.getToken());
        model.addAttribute("email", user.getEmail());

        return "/telegramAuthentication";
    }
}