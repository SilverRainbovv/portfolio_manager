package com.didenko.mapper;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.AssetTransaction;
import com.didenko.util.TwelveAssetDataImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.didenko.entity.PositionDirection.*;

@RequiredArgsConstructor
@Component
public class AssetTransactionReadDtoMapper
        implements Mapper<AssetTransaction, AssetTransactionReadDto> {

   private final TwelveAssetDataImporter twelveAssetDataImporter;

    @Override
    public AssetTransactionReadDto mapFrom(AssetTransaction object) {
        var currentPrice = object.getClosePrice() != null ? object.getClosePrice() : twelveAssetDataImporter.getAssetPrice(object.getAsset().getName());
        return AssetTransactionReadDto.builder()
                .assetName(object.getAsset().getName())
                .positionDirection(object.getPositionDirection().name())
                .volume(object.getQuantity())
                .openPrice(object.getOpenPrice())
                .openDate(object.getOpenDate().toString())
                .closePrice(object.getClosePrice())
                .closeDate(object.getCloseDate() == null ? null : object.getCloseDate().toString())
                .currentPrice(currentPrice)
                .profit(object.getPositionDirection().name().equals(LONG.name())
                        ? currentPrice.multiply(object.getQuantity())
                            .subtract(object.getOpenPrice().multiply(object.getQuantity()))
                        : object.getOpenPrice().multiply(object.getQuantity())
                            .subtract(currentPrice.multiply(object.getQuantity())))
                .build();
    }
}
