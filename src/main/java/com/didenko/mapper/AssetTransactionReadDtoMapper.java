package com.didenko.mapper;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.AssetTransaction;
import org.springframework.stereotype.Component;

@Component
public class AssetTransactionReadDtoMapper
        implements Mapper<AssetTransaction, AssetTransactionReadDto> {

    @Override
    public AssetTransactionReadDto mapFrom(AssetTransaction object) {
        return AssetTransactionReadDto.builder()
                .positionDirection(object.getPositionDirection().name())
                .quantity(object.getQuantity().toPlainString())
                .openPrice(object.getOpenPrice().toPlainString())
                .openDate(object.getOpenDate().toString())
                .closePrice(object.getClosePrice().toPlainString())
                .closeDate(object.getCloseDate().toString())
                .build();
    }
}
