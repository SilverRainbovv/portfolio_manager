package com.didenko.http.controller;

import com.didenko.service.AssetTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("transaction")
@Controller
public class AssetTransactionController {

    private final AssetTransactionService transactionService;

    @GetMapping("transactions/{portfolioId}")
    public String findByPortfolioId(Model model, @PathVariable(value = "portfolioId") Long portfolioId){

        var transactions = transactionService.findByPortfolioId(portfolioId);
        model.addAttribute("transactions", transactions);

        //TODO create page with portfolio transactions

        return "/transactions/transactions";
    }

    //TODO add transaction creation method

}
