package com.didenko.service;

import com.didenko.dto.AssetSumReadDto;
import com.didenko.entity.PositionDirection;
import com.didenko.mapper.AssetReadDtoMapper;
import com.didenko.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetReadDtoMapper assetReadDtoMapper;

    public List<AssetSumReadDto> findByPortfolioId(Long portfolioId){

        var assetSums = assetRepository.getAllByPortfolioId(portfolioId).stream()
                .map(assetReadDtoMapper::mapFrom).toList();

        return assetSums.stream()
                .map(this::divideByDirection)
                .flatMap(List::stream)
                .toList();
    }

    private List<AssetSumReadDto> divideByDirection(AssetSumReadDto assetSumReadDto){
        AssetSumReadDto longSum = new AssetSumReadDto();
        AssetSumReadDto shortSum = new AssetSumReadDto();

        longSum.setName(assetSumReadDto.getName());
        longSum.setDirection(PositionDirection.LONG.name());
        longSum.setLongQuantity(assetSumReadDto.getLongQuantity());
        longSum.setLongOpenPrice(assetSumReadDto.getLongOpenPrice());
        longSum.setLongClosePrice(assetSumReadDto.getLongClosePrice());

        shortSum.setName(assetSumReadDto.getName());
        shortSum.setDirection(PositionDirection.SHORT.name());
        shortSum.setShortQuantity(assetSumReadDto.getShortQuantity());
        shortSum.setShortOpenPrice(assetSumReadDto.getShortOpenPrice());
        shortSum.setShortClosePrice(assetSumReadDto.getShortClosePrice());

        return List.of(longSum, shortSum);
    }

}
