package com.didenko.http.rest;

import com.didenko.service.TelegramAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/telegram")
@RequiredArgsConstructor
@RestController
public class TelegramAuthenticationRestController {

    private final TelegramAuthenticationService tgAuthService;

    @PostMapping("/authenticate")
    public String authenticate(@RequestHeader(value = "email") String email,
                                               @RequestHeader(value = "token") String token,
                                               @RequestHeader(value = "chatId") Long chatId){

        return tgAuthService.tryAuthorize(email, token, chatId);
    }
}

