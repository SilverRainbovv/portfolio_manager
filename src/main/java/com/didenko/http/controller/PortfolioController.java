package com.didenko.http.controller;

import com.didenko.dto.PortfolioReadDto;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.PositionDirection;
import com.didenko.service.AssetService;
import com.didenko.service.AssetTransactionService;
import com.didenko.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AssetService assetService;
    private final AssetTransactionService assetTransactionService;

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long id){
        var portfolioAssets = assetService.findByPortfolioId(id);

        model.addAttribute("direction", PositionDirection.values());
        model.addAttribute("assets", portfolioAssets);
        model.addAttribute(portfolioService.getByUserId(id));

        return "/portfolio/portfolioPage";
    }

}
