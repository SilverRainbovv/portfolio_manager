package com.didenko.http.controller;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.dto.PortfolioCreateEditDto;
import com.didenko.dto.PositionDto;
import com.didenko.entity.PositionDirection;
import com.didenko.entity.TransactionState;
import com.didenko.service.AssetService;
import com.didenko.service.AssetTransactionService;
import com.didenko.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final AssetService assetService;
    private final AssetTransactionService transactionService;

    @GetMapping("/create")
    public String createPage(){
        return "/portfolio/portfolioCreatePage";
    }

    @PostMapping("/create")
    public String createPortfolio(@ModelAttribute PortfolioCreateEditDto portfolio,
                                  @AuthenticationPrincipal UserDetails userDetails){
        portfolio.setUsername(userDetails.getUsername());
        portfolioService.createPortfolio(portfolio);
        return "";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long portfolioId) {

        var portfolioTransactions = transactionService
                .findByPortfolioIdAndTransactionState(portfolioId, TransactionState.OPEN);

        if (!portfolioTransactions.isEmpty()) {
            var positions = convertToPositions(portfolioTransactions);
            positions.sort(Comparator.comparing(PositionDto::getAssetName));

            model.addAttribute("positions", positions);
        }

        model.addAttribute("portfolioId", portfolioId);

        return "/portfolio/portfolioPage";
    }

    private List<PositionDto> convertToPositions(List<AssetTransactionReadDto> transactions) {

        var positions = new ArrayList<PositionDto>();

        var transactionsByName = transactions.stream()
                .collect(Collectors.groupingBy(AssetTransactionReadDto::getAssetName));

        transactionsByName.keySet().forEach(name -> {

            var currentPrice = transactionsByName.get(name).get(0).getCurrentPrice();
            var openDate = transactionsByName.get(name).get(0).getOpenDate();

                    var longs = transactionsByName.values().stream()
                            .flatMap(List::stream)
                            .filter(t -> t.getAssetName().equals(name))
                            .filter(t -> t.getPositionDirection().equals(PositionDirection.LONG.name()))
                            .toList();

                    var shorts = transactionsByName.values().stream()
                            .flatMap(List::stream)
                            .filter(t -> t.getAssetName().equals(name))
                            .filter(t -> t.getPositionDirection().equals(PositionDirection.SHORT.name()))
                            .toList();

                    if (longs.size() > 0){
                        var longPosition = new PositionDto();
                        longPosition.setAssetName(name);
                        longPosition.setDirection(PositionDirection.LONG.name());
                        longPosition.setCurrentPrice(currentPrice);
                        longPosition.setOpenDate(openDate);

                        longPosition.setQuantity(
                                longs .stream().map(AssetTransactionReadDto::getVolume)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add));

                        longPosition.setOpenPrice(
                                longs.stream().map(AssetTransactionReadDto::getOpenPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .divide(new BigDecimal(longs.size()), RoundingMode.UNNECESSARY));

                        longPosition.setProfit(currentPrice.subtract(longPosition.getOpenPrice())
                                .multiply(longPosition.getQuantity()));

                        positions.add(longPosition);
                    }
                    if (shorts.size() > 0){
                        var shortPosition = new PositionDto();
                        shortPosition.setAssetName(name);
                        shortPosition.setDirection(PositionDirection.SHORT.name());
                        shortPosition.setCurrentPrice(currentPrice);
                        shortPosition.setOpenDate(openDate);

                        shortPosition.setQuantity(
                                shorts.stream().map(AssetTransactionReadDto::getVolume)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add));

                        shortPosition.setOpenPrice(
                                shorts.stream().map(AssetTransactionReadDto::getOpenPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .divide(new BigDecimal(longs.size()), RoundingMode.UNNECESSARY));

                        shortPosition.setProfit(shortPosition.getOpenPrice().subtract(currentPrice)
                                .multiply(shortPosition.getQuantity()));

                        positions.add(shortPosition);
                    }
                }
        );

        return positions;
    }

}
