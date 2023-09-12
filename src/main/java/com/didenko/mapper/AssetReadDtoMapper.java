package com.didenko.mapper;

import com.didenko.dto.AssetSumReadDto;
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
public class AssetReadDtoMapper implements Mapper<Asset, AssetSumReadDto> {

    private final AssetTransactionRepository assetTransactionRepository;

    @Override
    public AssetSumReadDto mapFrom(Asset object) {
        var transactionsGroupedByDirection = assetTransactionRepository
                .findAllByAssetId(object.getId()).stream()
                .collect(Collectors.groupingBy(AssetTransaction::getPositionDirection));

        var totalLongQuantity = getTotalQuantityByDirection(LONG, transactionsGroupedByDirection);
        var totalShortQuantity = getTotalQuantityByDirection(SHORT, transactionsGroupedByDirection);

        var totalLongOpenPrice = getOpenPriceByDirection(LONG, transactionsGroupedByDirection);
        var totalShortOpenPrice = getOpenPriceByDirection(SHORT, transactionsGroupedByDirection);
        var totalLongClosePrice = getClosePriceByDirection(LONG, transactionsGroupedByDirection);
        var totalShortClosePrice = getClosePriceByDirection(SHORT, transactionsGroupedByDirection);

        return AssetSumReadDto.builder()
                .name(object.getName())
//                .direction()
                .comments(object.getComments())
                .longQuantity(totalLongQuantity)
                .shortQuantity(totalShortQuantity)
                .longOpenPrice(totalLongOpenPrice)
                .shortOpenPrice(totalShortOpenPrice)
                .longClosePrice(totalLongClosePrice)
                .shortClosePrice(totalShortClosePrice)
                .build();
    }

    private String getTotalQuantityByDirection(PositionDirection direction,
                                               Map<PositionDirection, List<AssetTransaction>> transactionMap) {
        return transactionMap.get(direction).stream()
                .map(AssetTransaction::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add).toPlainString();
    }

    private String getOpenPriceByDirection(PositionDirection direction,
                                           Map<PositionDirection, List<AssetTransaction>> transactionMap) {
        var sorted = transactionMap.get(direction);
        var size = sorted.size();
        var priceSum = sorted.stream()
                .map(AssetTransaction::getOpenPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return priceSum.divide(new BigDecimal(size), RoundingMode.UNNECESSARY).toPlainString();
    }

    private String getClosePriceByDirection(PositionDirection direction,
                                            Map<PositionDirection, List<AssetTransaction>> transactionMap) {
        var sorted = transactionMap.get(direction);
        var size = sorted.size();
        var priceSum = transactionMap.get(direction).stream()
                .map(AssetTransaction::getClosePrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return priceSum.divide(new BigDecimal(size), RoundingMode.UNNECESSARY).toPlainString();
    }
}
