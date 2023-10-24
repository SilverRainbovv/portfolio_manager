package com.didenko.mapper;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.TransactionState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.didenko.entity.PositionDirection.*;

@RequiredArgsConstructor
@Component
public class AssetTransactionReadDtoMapper
       /* implements Mapper<AssetTransaction, AssetTransactionReadDto>*/ {

    public AssetTransactionReadDto mapFrom(AssetTransaction object, BigDecimal currentPrice) {
        return AssetTransactionReadDto.builder()
                .id(object.getId())
                .portfolioId(object.getAsset().getPortfolio().getId())
                .assetName(object.getAsset().getName())
                .assetType(object.getAsset().getAssetType())
                .positionDirection(object.getPositionDirection())
                .state(object.getState())
                .volume(object.getQuantity())
                .openPrice(object.getOpenPrice())
                .openDate(object.getOpenDate().toString())
                .closePrice(object.getClosePrice())
                .closeDate(object.getCloseDate() == null ? null : object.getCloseDate().toString())
                .currentPrice(currentPrice)
                .profit(getProfit(object, currentPrice))
                .build();
    }
    private BigDecimal getProfit(AssetTransaction transaction, BigDecimal currentPrice){
        var price = transaction.getState().equals(TransactionState.OPEN) ? currentPrice : transaction.getClosePrice();
        return transaction.getPositionDirection().equals(LONG)
                ? price.multiply(transaction.getQuantity())
                .subtract(transaction.getOpenPrice().multiply(transaction.getQuantity()))
                : transaction.getOpenPrice().multiply(transaction.getQuantity())
                .subtract(price.multiply(transaction.getQuantity()));
    }
}
