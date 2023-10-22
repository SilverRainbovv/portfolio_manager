package com.didenko.mapper;

import com.didenko.dto.PortfolioReadDto;
import com.didenko.dto.PositionDto;
import com.didenko.entity.Asset;
import com.didenko.entity.Portfolio;
import com.didenko.entity.TransactionState;
import com.didenko.repository.AssetRepository;
import com.didenko.service.AssetTransactionService;
import com.didenko.util.TransactionsToPositionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.crypto.dsig.TransformService;
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

            List<String> assetNames = assetRepository.getAllByPortfolioId(object.getId())
                    .stream().map(Asset::getName).toList();

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
                    .assets(assetNames)
                    .profit(portfolioProfit)
                    .build();
        }

}
