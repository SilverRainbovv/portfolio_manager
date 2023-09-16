package com.didenko.util;

import com.didenko.dto.AssetReadDto;
import com.didenko.entity.Asset;
import com.didenko.entity.AssetTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class PriceForAssetsRetriever {

    private final AssetDataImporter assetDataImporter;

    public Map<String, BigDecimal> retrieveForAssetTransactionsList(List<AssetTransaction> transactions) {

        return assetDataImporter.getAssetPrice(transactions.stream()
                .map(AssetTransaction::getAsset)
                .map(Asset::getName)
                .distinct().toList());

//        transactionDtos.forEach(transaction -> transaction.setCurrentPrice(names.get(transaction.getAssetName())));
    }

    public List<AssetReadDto> retrieveForAssetDtosList(List<AssetReadDto> assetDtos) {

        var assetNamePriceMap = assetDataImporter.getAssetPrice(assetDtos.stream()
                .map(AssetReadDto::getName)
                .distinct().toList());

        assetDtos.forEach(asset -> asset.setCurrentPrice(assetNamePriceMap.get(asset.getName())));

        return assetDtos;
    }

}
