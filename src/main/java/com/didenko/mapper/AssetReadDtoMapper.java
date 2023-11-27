package com.didenko.mapper;

import com.didenko.dto.AssetReadDto;
import com.didenko.entity.Asset;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.PositionDirection;
import com.didenko.repository.AssetTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.didenko.entity.PositionDirection.*;

@RequiredArgsConstructor
@Component
public class AssetReadDtoMapper implements Mapper<Asset, AssetReadDto> {

    private final AssetTransactionRepository assetTransactionRepository;

    @Override
    public AssetReadDto mapFrom(Asset object) {
        Map<PositionDirection, List<AssetTransaction>> transactionsGroupedByDirection =
                assetTransactionRepository
                .findAllByAssetId(object.getId()).stream()
                .collect(Collectors.groupingBy(AssetTransaction::getPositionDirection));

        BigDecimal totalLongQuantity = getTotalQuantityByDirection(LONG, transactionsGroupedByDirection);
        BigDecimal totalShortQuantity = getTotalQuantityByDirection(SHORT, transactionsGroupedByDirection);

        BigDecimal totalLongOpenPrice = getOpenPriceByDirection(LONG, transactionsGroupedByDirection);
        BigDecimal totalShortOpenPrice = getOpenPriceByDirection(SHORT, transactionsGroupedByDirection);

        BigDecimal totalCloseLongPrice = getClosePriceByDirection(LONG, transactionsGroupedByDirection);
        BigDecimal totalCloseShortPrice = getClosePriceByDirection(SHORT, transactionsGroupedByDirection);

        return AssetReadDto.builder()
                .id(object.getId().toString())
                .name(object.getName())
                .assetType(object.getAssetType().name())
                .comments(object.getComments())
                .longQuantity(totalLongQuantity)
                .shortQuantity(totalShortQuantity)
                .longOpenPrice(totalLongOpenPrice)
                .shortOpenPrice(totalShortOpenPrice)
                .longClosePrice(totalCloseLongPrice)
                .shortClosePrice(totalCloseShortPrice)
                .build();
    }

    private BigDecimal getTotalQuantityByDirection(PositionDirection direction,
                                               Map<PositionDirection, List<AssetTransaction>> transactionMap) {

        return transactionMap.get(direction) == null ? null :
                transactionMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(direction))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .map(AssetTransaction::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getOpenPriceByDirection(PositionDirection direction,
                                           Map<PositionDirection, List<AssetTransaction>> transactionMap) {

        List<AssetTransaction> transactionsByDirection = transactionMap.get(direction);
        if (transactionsByDirection == null) return null;
        int size = transactionsByDirection.size();
        BigDecimal priceSum = transactionsByDirection.stream()
                .map(AssetTransaction::getOpenPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return priceSum.divide(new BigDecimal(size), RoundingMode.HALF_UP);
    }

    private BigDecimal getClosePriceByDirection(PositionDirection direction,
                                            Map<PositionDirection, List<AssetTransaction>> transactionMap) {
        List<AssetTransaction> transactionsByDirection = transactionMap.get(direction);
        if (transactionsByDirection == null) return null;
        int size = transactionsByDirection.size();
        BigDecimal priceSum = transactionMap.get(direction).stream()
                .map(AssetTransaction::getClosePrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return priceSum.divide(new BigDecimal(size), RoundingMode.HALF_UP);
    }
}
