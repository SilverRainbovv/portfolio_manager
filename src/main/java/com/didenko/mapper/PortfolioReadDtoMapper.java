package com.didenko.mapper;

import com.didenko.dto.PortfolioReadDto;
import com.didenko.dto.PositionDto;
import com.didenko.entity.Asset;
import com.didenko.entity.Portfolio;
import com.didenko.repository.AssetRepository;
import com.didenko.service.AssetTransactionService;
import com.didenko.util.TransactionsToPositionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class  PortfolioReadDtoMapper implements Mapper<Portfolio, PortfolioReadDto> {
        private final AssetRepository assetRepository;
        private final AssetTransactionService transactionService;
        private final TransactionsToPositionConverter transactionsToPositionConverter;

        @Override
        public PortfolioReadDto mapFrom(Portfolio object) {

            var assets = assetRepository.getAllByPortfolioId(object.getId());

            StringBuilder assetNamesBuilder = new StringBuilder();

            assetRepository.getAllByPortfolioId(object.getId())
                    .forEach(asset -> assetNamesBuilder.append(asset.getName() + " "));

            var portfolioTransactions = transactionService
                    .findByPortfolioId(object.getId());

            var portfolioProfit = new BigDecimal(0);
            if (!portfolioTransactions.isEmpty()) {
                var positions = transactionsToPositionConverter.convert(portfolioTransactions);
                portfolioProfit = positions.stream().map(PositionDto::getProfit)
                        .reduce(new BigDecimal(0), BigDecimal::add);
            }

            return PortfolioReadDto.builder()
                    .id(object.getId())
                    .name(object.getName())
                    .description(object.getDescription())
                    .assets(assetNamesBuilder.toString())
                    .profit(portfolioProfit)
                    .build();
        }

}
