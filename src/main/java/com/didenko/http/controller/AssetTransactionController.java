package com.didenko.http.controller;

import com.didenko.dto.AssetTransactionCreateEditDto;
import com.didenko.entity.AssetType;
import com.didenko.entity.PositionDirection;
import com.didenko.entity.TransactionState;
import com.didenko.service.AssetTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RequestMapping("transaction")
@Controller
public class AssetTransactionController {

    private final AssetTransactionService transactionService;

    @GetMapping("transactions")
    public String findByPortfolioId(Model model, @RequestParam(value = "portfolioId") Long portfolioId){

        var transactions = transactionService.findByPortfolioId(portfolioId);
        model.addAttribute("openTransactions", transactions.stream()
                .filter(transaction -> transaction.getState().equals(TransactionState.OPEN)).toList());
        model.addAttribute("closedTransactions", transactions.stream()
                .filter(transaction -> transaction.getState().equals(TransactionState.CLOSED)).toList());

        return "/transaction/transactions";
    }

    @GetMapping("transaction")
    public String create(Model model, @RequestParam(value = "portfolioId") Long portfolioId){

        addBasicInfoToModel(model, portfolioId);

        return "/transaction/transaction";
    }

    @GetMapping("transaction/update/{id}")
    public String edit(Model model, @PathVariable Long id) {

        var transaction = transactionService.findByTransactionId(id);

        transaction.ifPresentOrElse(t -> {
                    model.addAttribute("transaction", t);
                    addBasicInfoToModel(model, t.getPortfolioId());
                },
                () -> {throw new ResponseStatusException(HttpStatus.NOT_FOUND);});

        return "/transaction/transactionUpdate";
    }

    @PostMapping("transaction")
    public String create(@ModelAttribute AssetTransactionCreateEditDto createEditDto,
                         @RequestParam(value = "portfolioId") Long portfolioId){

        createEditDto.setPortfolioId(portfolioId);
        transactionService.save(createEditDto);

        return "redirect:/portfolio/" + portfolioId;
    }

    @PostMapping("transaction/update")
    public String edit(@ModelAttribute AssetTransactionCreateEditDto createEditDto,
                       @RequestParam(value = "portfolioId") Long portfolioId,
                       @RequestParam(value = "transactionId") Long transactionId){
        createEditDto.setPortfolioId(portfolioId);
        createEditDto.setId(transactionId);
        transactionService.update(createEditDto);

        return "redirect:/portfolio/" + portfolioId;
    }

    private void addBasicInfoToModel(Model model, Long portfolioId){
        model.addAttribute("portfolioId", portfolioId);
        model.addAttribute("assetTypes", AssetType.values());
        model.addAttribute("positionDirections", PositionDirection.values());
    }

}
