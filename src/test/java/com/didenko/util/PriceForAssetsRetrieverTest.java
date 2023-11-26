package com.didenko.util;

import com.didenko.dto.AssetReadDto;
import com.didenko.entity.Asset;
import com.didenko.entity.AssetTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PriceForAssetsRetrieverTest {

    //    ASSET TRANSACTIONS CASES DATA
    private static final AssetTransaction TRANSACTION_1 = AssetTransaction.builder()
            .asset(Asset.builder().name("test1").build())
            .build();
    private static final AssetTransaction TRANSACTION_2 = AssetTransaction.builder()
            .asset(Asset.builder().name("test2").build())
            .build();
    private static final List<AssetTransaction> TRANSACTIONS_LIST =
            List.of(TRANSACTION_1, TRANSACTION_2);
    private static final Page<AssetTransaction> TRANSACTIONS_PAGE =
            new PageImpl<>(List.of(TRANSACTION_1, TRANSACTION_2));
    private static final Map<String, BigDecimal> EXPECTED_TRANSACTION_LIST =
            Map.of("test1", new BigDecimal(10), "test2", new BigDecimal(20));


    //    ASSET READ DTO CASES DATA
    private static final AssetReadDto ASSET_READ_DTO_1 = AssetReadDto.builder().name("test1").build();
    private static final AssetReadDto ASSET_READ_DTO_2 = AssetReadDto.builder().name("test2").build();
    private static final AssetReadDto ASSET_READ_DTO_W_PRICE_1 = AssetReadDto.builder().name("test1")
            .currentPrice(new BigDecimal(10)).build();
    private static final AssetReadDto ASSET_READ_DTO_W_PRICE_2 = AssetReadDto.builder().name("test2")
            .currentPrice(new BigDecimal(20)).build();
    private static final List<AssetReadDto> ASSET_READ_DTO_LIST = List.of(ASSET_READ_DTO_1, ASSET_READ_DTO_2);
    private static final List<AssetReadDto> EXPECTED_ASSET_READ_DTO_LIST =
            List.of(ASSET_READ_DTO_W_PRICE_1, ASSET_READ_DTO_W_PRICE_2);


    @Mock
    private AssetDataImporter dataImporter;
    @InjectMocks
    private PriceForAssetsRetriever priceForAssetsRetriever;

    @BeforeEach
    void prepareMock() {

        Mockito.doReturn(Map.of("test1", new BigDecimal(10), "test2", new BigDecimal(20)))
                .when(dataImporter).getAssetPrice(List.of("test1", "test2"));
    }

    @Test
    void retrieveForAssetTransactionsListCase() {

        assertEquals(EXPECTED_TRANSACTION_LIST,
                priceForAssetsRetriever.retrieveForAssetTransactionsList(TRANSACTIONS_LIST));
    }

    @Test
    void testRetrieveForAssetTransactionsPageCase() {

        assertEquals(EXPECTED_TRANSACTION_LIST,
                priceForAssetsRetriever.retrieveForAssetTransactionsList(TRANSACTIONS_PAGE));
    }

    @Test
    void retrieveForAssetDtosList() {

        assertEquals(EXPECTED_ASSET_READ_DTO_LIST,
                priceForAssetsRetriever.retrieveForAssetDtosList(ASSET_READ_DTO_LIST));
    }
}