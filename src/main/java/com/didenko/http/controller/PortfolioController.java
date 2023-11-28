package com.didenko.http.controller;

import com.didenko.dto.PortfolioCreateEditDto;
import com.didenko.dto.PositionDto;
import com.didenko.entity.TransactionState;
import com.didenko.entity.User;
import com.didenko.service.AssetTransactionService;
import com.didenko.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static com.didenko.entity.TransactionState.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AssetTransactionService transactionService;

    @GetMapping("/create")
    public String createPage(){
        return "/portfolio/portfolioCreatePage";
    }

    @PostMapping("/create")
    public String createPortfolio(@ModelAttribute PortfolioCreateEditDto portfolio,
                                  @AuthenticationPrincipal User userDetails){
        portfolio.setUser(userDetails);
        portfolioService.createPortfolio(portfolio);
        return "redirect:/user/" + userDetails.getId();
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long portfolioId,
                           @AuthenticationPrincipal User user) {

        verifyPortfolioBelongsToUser(portfolioId, user);

        Map<TransactionState, List<PositionDto>> transactionsMap =
                transactionService.findByPortfolioIdAndSortByTransactionState(portfolioId);

        if (transactionsMap.get(OPEN) != null)
            model.addAttribute("openPositions", transactionsMap.get(OPEN));

        if (transactionsMap.get(CLOSED) != null)
            model.addAttribute("closedPositions", transactionsMap.get(CLOSED));

        model.addAttribute("portfolioId", portfolioId);

        return "/portfolio/portfolioPage";
    }

    private void verifyPortfolioBelongsToUser(Long portfolioId, User user) throws AccessDeniedException {

        if (!portfolioService.verifyPortfolioBelongsToUser(portfolioId, user.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}