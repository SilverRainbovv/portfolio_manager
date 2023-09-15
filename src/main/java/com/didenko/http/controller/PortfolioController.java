package com.didenko.http.controller;

import com.didenko.dto.PortfolioReadDto;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.PositionDirection;
import com.didenko.service.AssetService;
import com.didenko.service.AssetTransactionService;
import com.didenko.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AssetService assetService;
    private final AssetTransactionService assetTransactionService;

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long id){
        var portfolio = portfolioService.getByUserId(id);
        if (portfolio.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var portfolioAssets = assetService.findByPortfolioId(id);

//        model.addAttribute("direction", PositionDirection.values());
        model.addAttribute("assets", portfolioAssets);
        model.addAttribute("portfolioInfo", portfolio);

        return "/portfolio/portfolioPage";
    }

}
