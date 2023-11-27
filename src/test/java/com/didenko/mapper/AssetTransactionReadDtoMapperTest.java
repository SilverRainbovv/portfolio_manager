package com.didenko.mapper;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class AssetTransactionReadDtoMapperTest {

    private static final BigDecimal CURRENT_PRICE = new BigDecimal("122.22").setScale(3, RoundingMode.HALF_UP);
    private static final Portfolio PORTFOLIO = Portfolio.builder()
            .id(1L)
            .build();
    private static final Asset ASSET = Asset.builder()
            .name("test asset")
            .assetType(AssetType.STOCK)
            .build();

    private static final AssetTransaction TRANSACTION = AssetTransaction.builder()
            .id(1L)
            .state(TransactionState.OPEN)
            .positionDirection(PositionDirection.LONG)
            .quantity(new BigDecimal(10))
            .openPrice(new BigDecimal(100))
            .openDate(LocalDateTime.of(2022, 10, 10, 5, 11))
            .closePrice(null)
            .closeDate(null)
            .build();

    AssetTransactionReadDto EXPECTED_TRANSACTION = AssetTransactionReadDto.builder()
            .id(1L)
            .portfolioId(1L)
            .assetType(AssetType.STOCK)
            .assetName("test asset")
            .positionDirection(PositionDirection.LONG)
            .state(TransactionState.OPEN)
            .volume(new BigDecimal(10).setScale(3, RoundingMode.HALF_UP))
            .openPrice(new BigDecimal(100).setScale(3, RoundingMode.HALF_UP))
            .openDate(LocalDateTime.of(2022, 10, 10, 5, 11).toString())
            .currentPrice(CURRENT_PRICE)
            .profit(new BigDecimal(222.20).setScale(3, RoundingMode.HALF_UP))
            .closePrice(null)
            .closeDate(null)
            .build();


    private static final AssetTransactionReadDtoMapper mapper = new AssetTransactionReadDtoMapper();

    @BeforeAll
    static void prepareTestData(){
        ASSET.setPortfolio(PORTFOLIO);
        TRANSACTION.setAsset(ASSET);
    }
    @Test
    void mapFrom() {

        AssetTransactionReadDto result = mapper.mapFrom(TRANSACTION, CURRENT_PRICE);
        assertEquals(result, EXPECTED_TRANSACTION);
    }
}