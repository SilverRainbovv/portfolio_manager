package com.didenko.service;

import com.didenko.dto.AssetReadDto;
import com.didenko.entity.PositionDirection;
import com.didenko.mapper.AssetReadDtoMapper;
import com.didenko.repository.AssetRepository;
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
    private final PriceForAssetsRetriever priceRetriever;

    @Transactional(readOnly = true)
    public List<AssetReadDto> findByPortfolioId(Long portfolioId) {

        var assetDtos = assetRepository.getAllByPortfolioId(portfolioId).stream()
                .map(assetReadDtoMapper::mapFrom).toList();

        var assetDtosWithPrice = priceRetriever.retrieveForAssetDtosList(assetDtos);

        return assetDtosWithPrice.stream()
                .map(this::divideByDirection)
                .flatMap(List::stream)
                .toList();
    }

    private List<AssetReadDto> divideByDirection(AssetReadDto object) {

        AssetReadDto assetSummaryLong = AssetReadDto.builder()
                .name(object.getName())
                .direction(PositionDirection.LONG.name())
                .longQuantity(object.getLongQuantity())
                .longOpenPrice(object.getLongOpenPrice())
                .currentPrice(object.getCurrentPrice())
                .longProfit(object.getCurrentPrice().multiply(object.getLongQuantity())
                        .subtract(object.getLongOpenPrice().multiply(object.getLongQuantity())))
                .build();

        AssetReadDto assetSummaryShort = AssetReadDto.builder()
                .name(object.getName())
                .direction(PositionDirection.SHORT.name())
                .shortQuantity(object.getShortQuantity())
                .shortOpenPrice(object.getShortOpenPrice())
                .currentPrice(object.getCurrentPrice())
                .shortProfit(object.getShortOpenPrice().multiply(object.getShortQuantity())
                        .subtract(object.getCurrentPrice().multiply(object.getShortQuantity())))
                .build();

        return List.of(assetSummaryLong, assetSummaryShort);
    }

}
