package com.didenko.http.rest;

import com.didenko.dto.PortfolioReadDto;
import com.didenko.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/portfolios")
@RestController
public class PortfolioRestController {

    private final PortfolioService portfolioService;

    @GetMapping
    public List<PortfolioReadDto> findAll(@RequestHeader(value = "chatId") Long chatId){

        return portfolioService.getByTelegramChatId(chatId);

    }

}
