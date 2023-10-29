package com.didenko.mapper;

import com.didenko.dto.AssetTransactionCreateEditDto;
import com.didenko.entity.PositionDirection;
import com.didenko.entity.TransactionState;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AssetTransactionCreateEditDtoMapperTest {

    private static final AssetTransactionCreateEditDto transactionDto =
            AssetTransactionCreateEditDto.builder()
                    .id(1L)
                    .portfolioId(1L)
                    .assetName("test asset")
                    .direction(PositionDirection.LONG)
                    .transactionState(TransactionState.OPEN)
                    .openPrice("100")
                    .volume("10")
                    .openDate(LocalDateTime.of(2022, 10, 10, 5, 11))
                    .closePrice(null)
                    .closeDate(null)
                    .build();

    @Test
    public void mapFromDtoTest(){

    }

}