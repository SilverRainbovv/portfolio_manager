package com.didenko.mapper;

import com.didenko.dto.AssetTransactionCreateEditDto;
import com.didenko.entity.Asset;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.Portfolio;
import com.didenko.entity.TransactionState;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class AssetTransactionCreateEditDtoMapper implements Mapper<AssetTransactionCreateEditDto, AssetTransaction> {
    @Override
    public AssetTransaction mapFrom(AssetTransactionCreateEditDto object) {
        var asset = Asset.builder()
                .name(object.getAssetName())
                .assetType(object.getAssetType())
                .portfolio(Portfolio.builder()
                        .id(object.getPortfolioId())
                        .build())
                .build();
        BigDecimal closePrice = object.getClosePrice().isEmpty() ? null : new BigDecimal(object.getClosePrice());
        LocalDateTime closeDate = object.getCloseDate() == null ? null : object.getCloseDate();
        var transaction = AssetTransaction.builder()
                .id(object.getId())
                .state(closePrice == null ? TransactionState.OPEN : TransactionState.CLOSED)
                .positionDirection(object.getDirection())
                .quantity(new BigDecimal(object.getVolume()))
                .openPrice(new BigDecimal(object.getOpenPrice()))
                .openDate(object.getOpenDate())
                .closePrice(closePrice)
                .closeDate(closeDate)
                .build();
        transaction.setAsset(asset);

        return transaction;
    }

}
