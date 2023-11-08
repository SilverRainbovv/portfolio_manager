package com.didenko.http.rest;

import com.didenko.entity.User;
import com.didenko.service.TelegramAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/telegram")
@RequiredArgsConstructor
@RestController
public class TelegramRestController {

    private final TelegramAuthenticationService tgAuthService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestHeader(value = "email") String email,
                                       @RequestHeader(value = "token") String token,
                                       @RequestHeader(value = "chatId") Long chatId){

        String message = tgAuthService.tryAuthorize(email, token, chatId);
        return ResponseEntity.ok(message);
    }

}
