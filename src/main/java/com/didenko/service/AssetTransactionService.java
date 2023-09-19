package com.didenko.service;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.TransactionState;
import com.didenko.mapper.AssetTransactionReadDtoMapper;
import com.didenko.repository.AssetTransactionRepository;
import com.didenko.util.PriceForAssetsRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AssetTransactionService {

    private final AssetTransactionRepository transactionRepository;
    private final AssetTransactionReadDtoMapper assetTransactionReadDtoMapper;
    private final PriceForAssetsRetriever retriever;

    public List<AssetTransactionReadDto> findByAssetId(Long assetId) {
        var assetTransactions = transactionRepository.findAllByAssetId(assetId);

        return setCurrentPriceAndMapToDto(assetTransactions);
    }

    public List<AssetTransactionReadDto> findByPortfolioId(Long portfolioId) {
        var assetTransactions = transactionRepository.findAllByPortfolioId(portfolioId);

        return setCurrentPriceAndMapToDto(assetTransactions);
    }

    public List<AssetTransactionReadDto> findByPortfolioIdAndTransactionState(Long portfolioId, TransactionState state) {
        var assetTransactions = transactionRepository.findAllByPortfolioIdAndTransactionState(portfolioId, state);

        return setCurrentPriceAndMapToDto(assetTransactions);
    }
    private List<AssetTransactionReadDto> setCurrentPriceAndMapToDto(List<AssetTransaction> assetTransactions){

        var transactionNamePriceMap = retriever.retrieveForAssetTransactionsList(assetTransactions);

        return assetTransactions.stream()
                .map(transaction -> {
                    var price = transactionNamePriceMap.get(transaction.getAsset().getName());
                    return assetTransactionReadDtoMapper.mapFrom(transaction, price);
                })
                .toList();
    }

}