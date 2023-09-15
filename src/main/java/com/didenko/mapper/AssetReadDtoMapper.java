package com.didenko.mapper;

import com.didenko.dto.AssetReadDto;
import com.didenko.entity.Asset;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.PositionDirection;
import com.didenko.repository.AssetTransactionRepository;
import com.didenko.util.TwelveAssetDataImporter;
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
        var transactionsGroupedByDirection = assetTransactionRepository
                .findAllByAssetId(object.getId()).stream()
                .collect(Collectors.groupingBy(AssetTransaction::getPositionDirection));

        var totalLongQuantity = getTotalQuantityByDirection(LONG, transactionsGroupedByDirection);
        var totalShortQuantity = getTotalQuantityByDirection(SHORT, transactionsGroupedByDirection);

        var totalLongOpenPrice = getOpenPriceByDirection(LONG, transactionsGroupedByDirection);
        var totalShortOpenPrice = getOpenPriceByDirection(SHORT, transactionsGroupedByDirection);

        return AssetReadDto.builder()
                .name(object.getName())
                .assetType(object.getAssetType().name())
                .comments(object.getComments())
                .longQuantity(totalLongQuantity)
                .shortQuantity(totalShortQuantity)
                .longOpenPrice(totalLongOpenPrice)
                .shortOpenPrice(totalShortOpenPrice)
                .build();
    }

    private BigDecimal getTotalQuantityByDirection(PositionDirection direction,
                                               Map<PositionDirection, List<AssetTransaction>> transactionMap) {
        return transactionMap.get(direction).stream()
                .map(AssetTransaction::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getOpenPriceByDirection(PositionDirection direction,
                                           Map<PositionDirection, List<AssetTransaction>> transactionMap) {
        var sorted = transactionMap.get(direction);
        var size = sorted.size();
        var priceSum = sorted.stream()
                .map(AssetTransaction::getOpenPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return priceSum.divide(new BigDecimal(size), RoundingMode.UNNECESSARY);
    }

    private BigDecimal getClosePriceByDirection(PositionDirection direction,
                                            Map<PositionDirection, List<AssetTransaction>> transactionMap) {
        var sorted = transactionMap.get(direction);
        var size = sorted.size();
        var priceSum = transactionMap.get(direction).stream()
                .map(AssetTransaction::getClosePrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return priceSum.divide(new BigDecimal(size), RoundingMode.UNNECESSARY);
    }
}
