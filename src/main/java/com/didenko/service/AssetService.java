package com.didenko.service;

import com.didenko.dto.AssetReadDto;
import com.didenko.entity.PositionDirection;
import com.didenko.mapper.AssetReadDtoMapper;
import com.didenko.repository.AssetRepository;
import com.didenko.util.AssetDataImporter;
import com.didenko.util.PriceForAssetsRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetReadDtoMapper assetReadDtoMapper;
    private final AssetDataImporter assetDataImporter;
    private final PriceForAssetsRetriever priceRetriever;
    @Transactional(readOnly = true)
    public List<AssetReadDto> findByPortfolioId(Long portfolioId){

        var assetDtos = assetRepository.getAllByPortfolioId(portfolioId).stream()
                .map(assetReadDtoMapper::mapFrom).toList();

        var assetDtosWithPrice = priceRetriever.retrieveForAssetDtosList(assetDtos);

        return assetDtosWithPrice.stream()
                .map(this::divideByDirection)
                .flatMap(List::stream)
                .toList();
    }

    private List<AssetReadDto> divideByDirection(AssetReadDto object){
        AssetReadDto assetSummaryLong = new AssetReadDto();
        AssetReadDto assetSummaryShort = new AssetReadDto();

        assetSummaryLong.setName(object.getName());
        assetSummaryLong.setDirection(PositionDirection.LONG.name());
        assetSummaryLong.setLongQuantity(object.getLongQuantity());
        assetSummaryLong.setLongOpenPrice(object.getLongOpenPrice());
        assetSummaryLong.setCurrentPrice(object.getCurrentPrice());
        assetSummaryLong.setLongProfit(object.getCurrentPrice().multiply(object.getLongQuantity())
                .subtract(object.getLongOpenPrice().multiply(object.getLongQuantity())));

        assetSummaryShort.setName(object.getName());
        assetSummaryShort.setDirection(PositionDirection.SHORT.name());
        assetSummaryShort.setShortQuantity(object.getShortQuantity());
        assetSummaryShort.setShortOpenPrice(object.getShortOpenPrice());
        assetSummaryShort.setCurrentPrice(object.getCurrentPrice());
        assetSummaryShort.setShortProfit(object.getShortOpenPrice().multiply(object.getShortQuantity())
                .subtract(object.getCurrentPrice().multiply(object.getShortQuantity())));

        return List.of(assetSummaryLong, assetSummaryShort);
    }

}
