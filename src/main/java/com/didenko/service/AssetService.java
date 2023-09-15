package com.didenko.service;

import com.didenko.dto.AssetReadDto;
import com.didenko.entity.PositionDirection;
import com.didenko.mapper.AssetReadDtoMapper;
import com.didenko.repository.AssetRepository;
import com.didenko.util.TwelveAssetDataImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetReadDtoMapper assetReadDtoMapper;
    private final TwelveAssetDataImporter dataImporter;
    @Transactional(readOnly = true)
    public List<AssetReadDto> findByPortfolioId(Long portfolioId){

        var assets = assetRepository.getAllByPortfolioId(portfolioId).stream()
                .map(assetReadDtoMapper::mapFrom).toList();

        return assets.stream()
                .map(this::divideByDirection)
                .flatMap(List::stream)
                .toList();
    }

    private List<AssetReadDto> divideByDirection(AssetReadDto object){
        AssetReadDto assetSummaryLong = new AssetReadDto();
        AssetReadDto assetSummaryShort = new AssetReadDto();
        var currentPrice = dataImporter.getAssetPrice(object.getName());

        assetSummaryLong.setName(object.getName());
        assetSummaryLong.setDirection(PositionDirection.LONG.name());
        assetSummaryLong.setLongQuantity(object.getLongQuantity());
        assetSummaryLong.setLongOpenPrice(object.getLongOpenPrice());
        assetSummaryLong.setCurrentPrice(currentPrice);
        assetSummaryLong.setLongProfit(currentPrice.multiply(object.getLongQuantity())
                .subtract(object.getLongOpenPrice().multiply(object.getLongQuantity())));

        assetSummaryShort.setName(object.getName());
        assetSummaryShort.setDirection(PositionDirection.SHORT.name());
        assetSummaryShort.setShortQuantity(object.getShortQuantity());
        assetSummaryShort.setShortOpenPrice(object.getShortOpenPrice());
        assetSummaryShort.setCurrentPrice(currentPrice);
        assetSummaryShort.setShortProfit(object.getShortOpenPrice().multiply(object.getShortQuantity())
                .subtract(currentPrice.multiply(object.getShortQuantity())));

        return List.of(assetSummaryLong, assetSummaryShort);
    }

}
