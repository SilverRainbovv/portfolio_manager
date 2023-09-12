package com.didenko.mapper;

import com.didenko.dto.PortfolioReadDto;
import com.didenko.entity.Asset;
import com.didenko.entity.Portfolio;
import com.didenko.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PortfolioReadDtoMapper implements Mapper<Portfolio, PortfolioReadDto> {
        private final AssetRepository assetRepository;

        @Override
        public PortfolioReadDto mapFrom(Portfolio object) {

            var assets = assetRepository.getAllByPortfolioId(object.getId());


            List<String> assetNames = assetRepository.getAllByPortfolioId(object.getId())
                    .stream().map(Asset::getName).toList();

            return PortfolioReadDto.builder()
                    .id(object.getId())
                    .name(object.getName())
                    .description(object.getDescription())
                    .assets(assetNames)
                    .build();
        }

}
