package com.didenko.mapper;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.dto.PortfolioReadDto;
import com.didenko.dto.PositionDto;
import com.didenko.entity.*;
import com.didenko.repository.AssetRepository;
import com.didenko.service.AssetTransactionService;
import com.didenko.util.TransactionsToPositionConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PortfolioReadDtoMapperTest {
    private static final User USER = User.builder()
            .id(1L)
            .build();

    private static final Portfolio PORTFOLIO = Portfolio.builder()
            .id(1L)
            .user(USER)
            .name("portfolio")
            .description("Some Description")
            .build();

    private static final Asset ASSET_1 = Asset.builder()
            .id(1L)
            .assetType(AssetType.STOCK)
            .name("test")
            .comments("test comments")
            .build();

    private static final Asset ASSET_2 = Asset.builder()
            .id(1L)
            .assetType(AssetType.STOCK)
            .name("test2")
            .comments("test comments")
            .build();

    private static final AssetTransaction TRANSACTION = AssetTransaction.builder()
            .id(1L)
            .state(TransactionState.CLOSED)
            .positionDirection(PositionDirection.LONG)
            .quantity(new BigDecimal(10))
            .openPrice(new BigDecimal(50))
            .openDate(LocalDateTime.of
                    (2023, 8, 13, 15, 10, 10))
            .closePrice(new BigDecimal(55))
            .closeDate(LocalDateTime.of
                    (2023, 8, 13, 17, 10, 10))
            .build();
    private static final AssetTransaction TRANSACTION_2 = AssetTransaction.builder()
            .id(2L)
            .state(TransactionState.CLOSED)
            .positionDirection(PositionDirection.LONG)
            .quantity(new BigDecimal(10))
            .openPrice(new BigDecimal(50))
            .openDate(LocalDateTime.of
                    (2023, 8, 13, 15, 10, 10))
            .closePrice(new BigDecimal(55))
            .closeDate(LocalDateTime.of
                    (2023, 8, 13, 17, 10, 10))
            .build();

    private static final AssetTransactionReadDto ASSET_TRANSACTION_READ_DTO_1 = AssetTransactionReadDto.builder()
            .profit(new BigDecimal(50))
            .build();
    private static final AssetTransactionReadDto ASSET_TRANSACTION_READ_DTO_2 = AssetTransactionReadDto.builder()
            .profit(new BigDecimal(50))
            .build();

    private static final PositionDto POSITION_DTO_1 = PositionDto.builder()
            .profit(new BigDecimal(50L)).build();
    private static final PositionDto POSITION_DTO_2 = PositionDto.builder()
            .profit(new BigDecimal(50L)).build();

    private static final PortfolioReadDto EXPECTED = PortfolioReadDto.builder()
            .id(1L)
            .name("portfolio")
            .description("Some Description")
            .assets("test test2")
            .profit(new BigDecimal(100))
            .build();


    @Mock
    private AssetRepository assetRepository;
    @Mock
    private AssetTransactionService transactionService;
    @Mock
    private TransactionsToPositionConverter converter;
    @InjectMocks
    private PortfolioReadDtoMapper mapper;

    @BeforeAll
    public static void prepareData(){
        TRANSACTION.setAsset(ASSET_1);
        TRANSACTION_2.setAsset(ASSET_2);
        ASSET_1.setPortfolio(PORTFOLIO);
        ASSET_2.setPortfolio(PORTFOLIO);
    }

    @Test
    void mapFrom() {
        var assetList = List.of(ASSET_1, ASSET_2);
        var transactionDtoList = List.of(ASSET_TRANSACTION_READ_DTO_1, ASSET_TRANSACTION_READ_DTO_2);
        var positionList = List.of(POSITION_DTO_1, POSITION_DTO_2);

        var result = mapper.mapFrom(PORTFOLIO);
    }
}