package com.didenko.mapper;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.TransactionState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.didenko.entity.PositionDirection.*;

@RequiredArgsConstructor
@Component
public class AssetTransactionReadDtoMapper {

    public AssetTransactionReadDto mapFrom(AssetTransaction object, BigDecimal currentPrice) {
        return AssetTransactionReadDto.builder()
                .id(object.getId())
                .portfolioId(object.getAsset().getPortfolio().getId())
                .assetName(object.getAsset().getName())
                .assetType(object.getAsset().getAssetType())
                .positionDirection(object.getPositionDirection())
                .state(object.getState())
                .volume(object.getQuantity().setScale(3, RoundingMode.HALF_UP))
                .openPrice(object.getOpenPrice().setScale(3, RoundingMode.HALF_UP))
                .openDate(object.getOpenDate().toString())
                .closePrice(object.getClosePrice() == null ? null : object.getClosePrice().setScale(3, RoundingMode.HALF_UP))
                .closeDate(object.getCloseDate() == null ? null : object.getCloseDate().toString())
                .currentPrice(currentPrice.setScale(3, RoundingMode.HALF_UP))
                .profit(getProfit(object, currentPrice).setScale(3, RoundingMode.HALF_UP))
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
