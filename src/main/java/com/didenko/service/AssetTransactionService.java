package com.didenko.service;

import com.didenko.dto.AssetTransactionCreateEditDto;
import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.dto.PositionDto;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.TransactionState;
import com.didenko.mapper.AssetTransactionCreateEditDtoMapper;
import com.didenko.mapper.AssetTransactionReadDtoMapper;
import com.didenko.repository.AssetRepository;
import com.didenko.repository.AssetTransactionRepository;
import com.didenko.util.PriceForAssetsRetriever;
import com.didenko.util.TransactionsToPositionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.didenko.entity.TransactionState.CLOSED;
import static com.didenko.entity.TransactionState.OPEN;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AssetTransactionService {

    private final AssetTransactionRepository transactionRepository;
    private final AssetTransactionReadDtoMapper assetTransactionReadDtoMapper;
    private final PriceForAssetsRetriever retriever;
    private final AssetTransactionCreateEditDtoMapper assetTransactionCreateEditDtoMapper;
    private final AssetRepository assetRepository;
    private final TransactionsToPositionConverter transactionsToPositionConverter;

    public List<AssetTransactionReadDto> findByAssetId(Long assetId) {
        var assetTransactions = transactionRepository.findAllByAssetId(assetId);

        return setCurrentPriceAndMapToDto(assetTransactions);
    }

    public Page<AssetTransactionReadDto> findByPortfolioIdPageable(Long portfolioId, Pageable pageable) {
        var assetTransactions = transactionRepository.findAllByAssetPortfolioId(portfolioId, pageable);

        return setCurrentPriceAndMapToDto(assetTransactions, pageable);
    }

    public Page<AssetTransactionReadDto> findByAssetNameAndPortfolioId(Long portfolioId,
                                                                       Pageable pageable, String assetName) {
        var assetTransactions = transactionRepository.findAllByAssetNameAndAssetPortfolioId(assetName,
                portfolioId, pageable);

        return setCurrentPriceAndMapToDto(assetTransactions, pageable);
    }

    public List<AssetTransactionReadDto> findByPortfolioId(Long portfolioId) {
        var assetTransactions = transactionRepository.findAllByAssetPortfolioId(portfolioId);

        return setCurrentPriceAndMapToDto(assetTransactions);
    }

    public Map<TransactionState, List<PositionDto>> findByPortfolioIdAndSortByTransactionState(Long portfolioId) {

        List<AssetTransaction> assetTransactions = transactionRepository.findAllByAssetPortfolioId(portfolioId);

        List<AssetTransactionReadDto> transactionDtos = setCurrentPriceAndMapToDto(assetTransactions);

        Map<TransactionState, List<AssetTransactionReadDto>> transactionStateListMap = transactionDtos.stream()
                .sorted(Comparator.comparing(AssetTransactionReadDto::getAssetName))
                .collect(Collectors.groupingBy(AssetTransactionReadDto::getState));

        Map<TransactionState, List<PositionDto>> positionDtoMap = new HashMap<>();
        positionDtoMap.put(OPEN, transactionsToPositionConverter.convert(transactionStateListMap.get(OPEN)));
        positionDtoMap.put(CLOSED, transactionsToPositionConverter.convert(transactionStateListMap.get(CLOSED)));

        return positionDtoMap;
    }

    private Page<AssetTransactionReadDto> setCurrentPriceAndMapToDto(Page<AssetTransaction> assetTransactions,
                                                                     Pageable pageable) {

        if (assetTransactions.isEmpty()) return Page.empty();

        var transactionNamePriceMap = retriever.retrieveForAssetTransactionsList(assetTransactions);

        var content = assetTransactions.stream()
                .map(transaction -> {
                    var price = transactionNamePriceMap.get(transaction.getAsset().getName());
                    return assetTransactionReadDtoMapper.mapFrom(transaction, price);
                })
                .toList();

        return new PageImpl<>(content, pageable, assetTransactions.getTotalPages());
    }

    private List<AssetTransactionReadDto> setCurrentPriceAndMapToDto(List<AssetTransaction> assetTransactions) {

        if (assetTransactions.isEmpty()) return new ArrayList<>();

        var transactionNamePriceMap = retriever.retrieveForAssetTransactionsList(assetTransactions);

        return assetTransactions.stream()
                .map(transaction -> {
                    var price = transactionNamePriceMap.get(transaction.getAsset().getName());
                    return assetTransactionReadDtoMapper.mapFrom(transaction, price);
                })
                .toList();
    }

    @Transactional
    public void save(AssetTransactionCreateEditDto transactionDto) {
        var transaction = assetTransactionCreateEditDtoMapper.mapFrom(transactionDto);

        var persistedAsset = assetRepository.findByNameAndPortfolioId(transaction.getAsset().getName(),
                transaction.getAsset().getPortfolio().getId());
        if (persistedAsset.isPresent()) {
            transaction.setAsset(persistedAsset.get());
        } else {
            transaction.setAsset(assetRepository.save(transaction.getAsset()));
        }
        transactionRepository.save(transaction);
    }

    public Optional<AssetTransactionReadDto> findByTransactionId(Long id) {
        return transactionRepository.findById(id).map(t -> assetTransactionReadDtoMapper.mapFrom(t, t.getOpenPrice()));
    }

    @Transactional
    public void update(AssetTransactionCreateEditDto createEditDto) {
        AssetTransaction transaction = assetTransactionCreateEditDtoMapper.mapFrom(createEditDto);
        var volume = transaction.getQuantity();
        var openPrice = transaction.getOpenPrice();
        var closePrice = transaction.getClosePrice();
        var openDate = transaction.getOpenDate();
        var closeDate = transaction.getCloseDate();
        var state = closePrice == null ? OPEN : TransactionState.CLOSED;
        transactionRepository.updateById(transaction.getId(), volume, openPrice, closePrice, openDate, closeDate, state);
    }
}