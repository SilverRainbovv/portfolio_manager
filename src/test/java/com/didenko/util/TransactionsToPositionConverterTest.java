package com.didenko.util;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.dto.PositionDto;
import com.didenko.entity.PositionDirection;
import com.didenko.entity.TransactionState;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@SpringBootTest
class TransactionsToPositionConverterTest {

    private static final AssetTransactionReadDto OPEN_TRANSACTION = AssetTransactionReadDto.builder()
            .assetName("test1")
            .positionDirection(PositionDirection.LONG)
            .state(TransactionState.OPEN)
            .volume(new BigDecimal("1.0025"))
            .openPrice(new BigDecimal("51.223"))
            .openDate(LocalDateTime.of(2022, 6, 10, 5, 43).toString())
            .currentPrice(new BigDecimal("52.000"))
            .profit(new BigDecimal("0.779"))
            .build();
    private static final PositionDto OPEN_POSITION_DTO = PositionDto.builder()
            .assetName("test1")
            .direction(PositionDirection.LONG.name())
            .quantity(new BigDecimal("1.003"))
            .openPrice(new BigDecimal("51.223"))
            .openDate(LocalDateTime.of(2022, 6, 10, 5, 43).toString())
            .currentPrice(new BigDecimal("52.000"))
            .profit(new BigDecimal("0.779"))
            .build();
    private static final List<AssetTransactionReadDto> ASSET_TRANSACTION_READ_DTO_LIST_OPEN_CASE = List.of(OPEN_TRANSACTION);
    private static final List<PositionDto> EXPECTED_RESULT_FOR_OPEN_CASE = List.of(OPEN_POSITION_DTO);

    private static final AssetTransactionReadDto CLOSE_TRANSACTION = AssetTransactionReadDto.builder()
            .assetName("test1")
            .positionDirection(PositionDirection.SHORT)
            .state(TransactionState.CLOSED)
            .volume(new BigDecimal("2.000"))
            .openPrice(new BigDecimal("10.123"))
            .openDate(LocalDateTime.of(2022, 6, 10, 5, 43).toString())
            .closeDate(LocalDateTime.of(2022, 6, 11, 5, 43).toString())
            .closePrice(new BigDecimal("12.125"))
            .profit(new BigDecimal("0.779"))
            .build();
    private static final PositionDto CLOSE_POSITION_DTO = PositionDto.builder()
            .assetName("test1")
            .direction(PositionDirection.SHORT.name())
            .quantity(new BigDecimal("2.000"))
            .openPrice(new BigDecimal("10.123"))
            .openDate(LocalDateTime.of(2022, 6, 10, 5, 43).toString())
            .closeDate(LocalDateTime.of(2022, 6, 11, 5, 43).toString())
            .currentPrice(new BigDecimal("12.125"))
            .closePrice(new BigDecimal("12.125"))
            .profit(new BigDecimal("-4.004"))
            .build();
    private static final List<AssetTransactionReadDto> ASSET_TRANSACTION_READ_DTO_LIST_CLOSE_CASE = List.of(CLOSE_TRANSACTION);
    private static final List<PositionDto> EXPECTED_RESULT_FOR_CLOSE_CASE = List.of(CLOSE_POSITION_DTO);
    private static final TransactionsToPositionConverter converter = new TransactionsToPositionConverter();

    @Test
    void openTransactionCase() {
        Assertions.assertEquals(EXPECTED_RESULT_FOR_OPEN_CASE, converter.convert(ASSET_TRANSACTION_READ_DTO_LIST_OPEN_CASE));
    }

    @Test
    void closeTransactionCase() {
        Assertions.assertEquals(EXPECTED_RESULT_FOR_CLOSE_CASE, converter.convert(ASSET_TRANSACTION_READ_DTO_LIST_CLOSE_CASE));
    }
}