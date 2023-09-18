package com.didenko.http.controller;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.TransactionState;
import com.didenko.service.AssetTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("transaction")
@Controller
public class AssetTransactionController {

    private final AssetTransactionService transactionService;

    @GetMapping("transactions")
    public String findByPortfolioId(Model model, @RequestParam(value = "portfolioId") Long portfolioId){

        var transactions = transactionService.findByPortfolioId(portfolioId);
        model.addAttribute("openTransactions", transactions.stream()
                .filter(transaction -> transaction.getState().equals(TransactionState.OPEN.name())).toList());
        model.addAttribute("closedTransactions", transactions.stream()
                .filter(transaction -> transaction.getState().equals(TransactionState.CLOSED.name())).toList());

        //TODO create page with portfolio transactions

        return "/transaction/transactions";
    }

    //TODO add transaction creation method

}
