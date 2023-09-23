package com.didenko.http.controller;

import com.didenko.dto.AssetTransactionCreateEditDto;
import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.AssetType;
import com.didenko.entity.PositionDirection;
import com.didenko.entity.TransactionState;
import com.didenko.service.AssetTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        return "/transaction/transactions";
    }

    @GetMapping("transaction")
    public String newTransaction(Model model, @RequestParam(value = "portfolioId") Long portfolioId){

        model.addAttribute("portfolioId", portfolioId);
        model.addAttribute("assetTypes", AssetType.values());
        model.addAttribute("positionDirections", PositionDirection.values());

        return "/transaction/transaction";
    }

    @PostMapping("transaction")


    public String create(@ModelAttribute AssetTransactionCreateEditDto createEditDto,
                         @RequestParam(value = "portfolioId") Long portfolioId){

        createEditDto.setPortfolioId(portfolioId);
        transactionService.save(createEditDto);

        return "redirect:/portfolio/1";
    }


}
