package com.didenko.mapper;

import com.didenko.dto.AssetTransactionCreateEditDto;
import com.didenko.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AssetTransactionCreateEditDtoMapperTest {

    private static final AssetTransactionCreateEditDto TRANSACTION_CREATE_EDIT_DTO =
            AssetTransactionCreateEditDto.builder()
                    .id(1L)
                    .portfolioId(1L)
                    .assetName("test asset")
                    .assetType(AssetType.STOCK)
                    .direction(PositionDirection.LONG)
                    .transactionState(TransactionState.OPEN)
                    .openPrice("100")
                    .volume("10")
                    .openDate(LocalDateTime.of(2022, 10, 10, 5, 11))
                    .closePrice(null)
                    .closeDate(null)
                    .build();

    private static final Portfolio EXPECTED_PORTFOLIO = Portfolio.builder()
            .id(1L)
            .build();
    private static final Asset EXPECTED_ASSET = Asset.builder()
            .name("test asset")
            .assetType(AssetType.STOCK)
            .build();

    private static final AssetTransaction EXPECTED_TRANSACTION = AssetTransaction.builder()
            .id(1L)
            .state(TransactionState.OPEN)
            .positionDirection(PositionDirection.LONG)
            .quantity(new BigDecimal(10))
            .openPrice(new BigDecimal(100))
            .openDate(LocalDateTime.of(2022, 10, 10, 5, 11))
            .closePrice(null)
            .closeDate(null)
            .build();

    private static final AssetTransactionCreateEditDtoMapper mapper = new AssetTransactionCreateEditDtoMapper();

    @BeforeAll
    static void prepareTestData(){
        EXPECTED_ASSET.setPortfolio(EXPECTED_PORTFOLIO);
        EXPECTED_TRANSACTION.setAsset(EXPECTED_ASSET);
    }

    @Test
    public void mapFromDtoTest(){

        AssetTransaction result = mapper.mapFrom(TRANSACTION_CREATE_EDIT_DTO);
        assertEquals(EXPECTED_TRANSACTION, result);

    }

}