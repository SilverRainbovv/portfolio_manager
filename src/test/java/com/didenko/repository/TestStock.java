package com.didenko.repository;

import com.didenko.entity.Asset;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.PositionDirection;
import com.didenko.mapper.AssetTransactionReadDtoMapper;
import com.didenko.util.TwelveAssetDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class TestStock {

    private final AssetTransactionReadDtoMapper mapper;

    @Test
    public void test(){
        var asset = Asset.builder()
                .id(11L)
                .name("AAPL")
                .build();
        var transaction = AssetTransaction.builder()
                .positionDirection(PositionDirection.LONG)
                .quantity(new BigDecimal(2))
                .openPrice(new BigDecimal(100))
                .openDate(LocalDateTime.now())
                .closeDate(LocalDateTime.now())
                .closePrice(new BigDecimal(105))
                .build();

        var asset2 = Asset.builder()
                .id(12L)
                .name("AMZN")
                .build();
        var transaction2 = AssetTransaction.builder()
                .positionDirection(PositionDirection.SHORT)
                .quantity(new BigDecimal(2))
                .openPrice(new BigDecimal(100))
                .openDate(LocalDateTime.now())
                .closeDate(LocalDateTime.now())
                .build();

        transaction.setAsset(asset);
        transaction2.setAsset(asset2);

        var first = mapper.mapFrom(transaction);
        var second = mapper.mapFrom(transaction2);

        System.out.println();

    }

}
