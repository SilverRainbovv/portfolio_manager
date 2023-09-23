package com.didenko.mapper;

import com.didenko.dto.AssetTransactionCreateEditDto;
import com.didenko.entity.Asset;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.Portfolio;
import com.didenko.entity.TransactionState;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
        var transaction = AssetTransaction.builder()
                .state(TransactionState.OPEN)
                .positionDirection(object.getDirection())
                .quantity(new BigDecimal(object.getVolume()))
                .openPrice(new BigDecimal(object.getOpenPrice()))
                .openDate(object.getOpenDate())
                .build();
        transaction.setAsset(asset);

        return transaction;
    }
}
