package com.didenko.service;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.AssetTransaction;
import com.didenko.mapper.AssetTransactionReadDtoMapper;
import com.didenko.repository.AssetTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AssetTransactionService {

    private final AssetTransactionRepository transactionRepository;
    private final AssetTransactionReadDtoMapper assetTransactionReadDtoMapper;

    public List<AssetTransactionReadDto> findByAssetId(Long assetId){
        return transactionRepository.findAllByAssetId(assetId).stream()
                .map(assetTransactionReadDtoMapper::mapFrom).toList();
    }

    public List<AssetTransactionReadDto> findByPortfolioId(Long portfolioId){
        return transactionRepository.findAllByPortfolioId(portfolioId).stream()
                .map(assetTransactionReadDtoMapper::mapFrom).toList();
    }

}
