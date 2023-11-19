package com.didenko.http.controller;

import com.didenko.dto.AssetTransactionCreateEditDto;
import com.didenko.entity.*;
import com.didenko.service.AssetTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RequestMapping("transaction")
@Controller
public class AssetTransactionController {

    private final AssetTransactionService transactionService;

    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final Integer DEFAULT_PAGE = 0;
    private static final TransactionsSortingOrder DEFAULT_SORTING_ORDER = TransactionsSortingOrder.ASSET_NAME;

    @GetMapping("transactions/**")
    public String findByPortfolioId(Model model, @RequestParam(value = "portfolioId") Long portfolioId,
                                    @RequestParam(value = "findAsset", required = false) String assetName,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                    @RequestParam(value = "sortBy", required = false) TransactionsSortingOrder sortingOrder,
                                    @AuthenticationPrincipal User user){

        String currentAssetName = assetName == null ? "" : assetName;
        TransactionsSortingOrder currentSortingOrder = sortingOrder == null ? DEFAULT_SORTING_ORDER : sortingOrder;
        Integer currentPage = page == null ? DEFAULT_PAGE : page;
        Integer currentPageSize = getPageSize(PageSizeOptions.of(pageSize), user);
        Pageable pageable = PageRequest.of(currentPage, currentPageSize,
                Sort.by(currentSortingOrder.fieldName));

        var transactions = currentAssetName.isEmpty()
                ? transactionService.findByPortfolioIdPageable(portfolioId, pageable)
                : transactionService.findByAssetNameAndPortfolioId(portfolioId, pageable, assetName);

        model.addAttribute("pageSizeOptions", PageSizeOptions.values());
        model.addAttribute("sortingOrders", TransactionsSortingOrder.values());
        model.addAttribute("portfolioId", portfolioId);
        model.addAttribute("openTransactions", transactions.stream()
                .filter(transaction -> transaction.getState().equals(TransactionState.OPEN)).toList());
        model.addAttribute("closedTransactions", transactions.stream()
                .filter(transaction -> transaction.getState().equals(TransactionState.CLOSED)).toList());
        model.addAttribute("currentPageSize", currentPageSize);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("currentSortingOrder", currentSortingOrder);
        model.addAttribute("currentAssetName", currentAssetName);
        model.addAttribute("totalPages", transactions.getTotalPages());

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

    private Integer getPageSize(PageSizeOptions pageSize, User user){
        int currentPageSize = DEFAULT_PAGE_SIZE;
        if (pageSize != null){
            currentPageSize = pageSize.size;
            user.getUserPreferences().setPageSize(pageSize.size);
        } else {
            Integer userPageSize = user.getUserPreferences().getPageSize();
            if (userPageSize != null) currentPageSize = userPageSize;
        }
        return currentPageSize;
    }

}