package com.didenko.http.controller;

import com.didenko.dto.PortfolioReadDto;
import com.didenko.dto.UserCreateEditDto;
import com.didenko.entity.User;
import com.didenko.service.PortfolioService;
import com.didenko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;

    @GetMapping
    public String userRedirect(@AuthenticationPrincipal User user){
        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal User user) {

        if (!id.equals(user.getId()))
            return "redirect:/user/" + user.getId();

        List<PortfolioReadDto> portfolios = portfolioService.getByUserId(id);

        return userService.findById(id)
                .map(maybeUser -> {
                    model.addAttribute("user", maybeUser);
                    model.addAttribute("portfolios", portfolios);
                    model.addAttribute("chartData", prepareChartData(portfolios));
                    return "user/userPage";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Map<String, BigDecimal> prepareChartData(List<PortfolioReadDto> portfolios) {
        Map<String, BigDecimal> nameToProfit = new HashMap<>();
        portfolios.forEach(portfolio -> {
            nameToProfit.put(portfolio.getName(), portfolio.getProfit());
        });
        return nameToProfit;
    }
}
