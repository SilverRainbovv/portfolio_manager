package com.didenko.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TwelveAssetDataImporterTest {

    private static final String TICKER_1 = "IBM";
    private static final String TICKER_2 = "INT_C";
    private static final List<String> MULTIPLE_TICKERS = List.of(TICKER_1, TICKER_2);
    private static final List<String> SINGLE_TICKER = List.of(TICKER_1);

    @Autowired
    TwelveAssetDataImporter assetDataImporter;
    @Test
    void checkHasApiKey(){
        assertNotNull(assetDataImporter.getApiKey());
    }

    @Test
    void testSingleTicker(){
        Map<String, BigDecimal> result = assetDataImporter.getAssetPrice(SINGLE_TICKER);

        assertNotNull(result);
        assert(result.keySet().stream().toList().equals(SINGLE_TICKER));
        assert(result.size() == SINGLE_TICKER.size());
    }

    @Test
    void testMultipleTickers() {
        Map<String, BigDecimal> result = assetDataImporter.getAssetPrice(MULTIPLE_TICKERS);

        assertNotNull(result);
        assert(result.keySet().stream().toList().equals(MULTIPLE_TICKERS));
        assert(result.size() == MULTIPLE_TICKERS.size());

    }
}