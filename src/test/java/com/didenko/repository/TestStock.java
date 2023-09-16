package com.didenko.repository;

import com.didenko.entity.Asset;
import com.didenko.entity.AssetTransaction;
import com.didenko.entity.PositionDirection;
import com.didenko.mapper.AssetTransactionReadDtoMapper;
import com.didenko.util.AssetDataImporter;
import com.didenko.util.TwelveAssetDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class TestStock {

    private final AssetDataImporter dataImporter;

    @Test
    public void test(){
        dataImporter.getAssetPrice(List.of("AAPL", "AMZN"));
    }

}
