package com.didenko.mapper;

import com.didenko.dto.AssetReadDto;
import com.didenko.entity.*;
import com.didenko.repository.AssetTransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AssetReadDtoMapperTest {

    @Mock
    private AssetTransactionRepository repository;
    @InjectMocks
    private AssetReadDtoMapper mapper;

    private static final User USER = User.builder().build();
    private static final Portfolio PORTFOLIO = Portfolio.builder()
            .id(1L)
            .user(USER)
            .name("portfolio")
            .build();

    private static final Asset ASSET = Asset.builder()
            .id(1L)
            .assetType(AssetType.STOCK)
            .name("test")
            .portfolio(PORTFOLIO)
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

    private static final AssetReadDto EXPECTED = AssetReadDto.builder()
            .id("1")
            .name("test")
            .assetType(AssetType.STOCK.name())
            .comments("test comments")
            .longQuantity(new BigDecimal(10))
            .shortQuantity(null)
            .longOpenPrice(new BigDecimal(50))
            .shortOpenPrice(null)
            .currentPrice(null)
            .longClosePrice(new BigDecimal(55))
            .build();

    @Test
    public void mapFromAssetTest() {

        Mockito.doReturn(List.of(TRANSACTION)).when(repository).findAllByAssetId(Mockito.any());

        ASSET.addTransaction(TRANSACTION);

        AssetReadDto mappingResult = mapper.mapFrom(ASSET);

        Assertions.assertEquals(EXPECTED, mappingResult);
    }

}