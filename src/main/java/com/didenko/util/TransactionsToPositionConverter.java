package com.didenko.util;

import com.didenko.dto.AssetTransactionReadDto;
import com.didenko.dto.PositionDto;
import com.didenko.entity.PositionDirection;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@Component
public class TransactionsToPositionConverter {

    public List<PositionDto> convert(List<AssetTransactionReadDto> transactions) {

        var positions = new ArrayList<PositionDto>();

        var transactionsByName = transactions.stream()
                .collect(Collectors.groupingBy(AssetTransactionReadDto::getAssetName));

        transactionsByName.keySet().forEach(name -> {

                    var currentPrice = transactionsByName.get(name).get(0).getCurrentPrice();
                    var openDate = transactionsByName.get(name).get(0).getOpenDate();

                    var longs = transactionsByName.values().stream()
                            .flatMap(List::stream)
                            .filter(t -> t.getAssetName().equals(name))
                            .filter(t -> t.getPositionDirection().equals(PositionDirection.LONG))
                            .toList();

                    var shorts = transactionsByName.values().stream()
                            .flatMap(List::stream)
                            .filter(t -> t.getAssetName().equals(name))
                            .filter(t -> t.getPositionDirection().equals(PositionDirection.SHORT))
                            .toList();

                    if (!longs.isEmpty()){
                        var longPosition = new PositionDto();
                        longPosition.setAssetName(name);
                        longPosition.setDirection(PositionDirection.LONG.name());
                        longPosition.setCurrentPrice(currentPrice);
                        longPosition.setOpenDate(openDate);

                        longPosition.setQuantity(
                                longs .stream().map(AssetTransactionReadDto::getVolume)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add));

                        var sum = new ArrayList<BigDecimal>();
                        var totalVolume = new ArrayList<BigDecimal>();
                        longs.forEach(current -> {
                            sum.add(current.getOpenPrice().multiply(current.getVolume()));
                            totalVolume.add(current.getVolume());
                        });

                        longPosition.setOpenPrice(sum.stream().reduce(new BigDecimal(0), BigDecimal::add)
                                .divide(totalVolume.stream().reduce(new BigDecimal(0), BigDecimal::add),
                                        RoundingMode.FLOOR));

                        longPosition.setProfit(currentPrice.subtract(longPosition.getOpenPrice())
                                .multiply(longPosition.getQuantity()));

                        positions.add(longPosition);
                    }
                    if (!shorts.isEmpty()){
                        var shortPosition = new PositionDto();
                        shortPosition.setAssetName(name);
                        shortPosition.setDirection(PositionDirection.SHORT.name());
                        shortPosition.setCurrentPrice(currentPrice);
                        shortPosition.setOpenDate(openDate);

                        shortPosition.setQuantity(
                                shorts.stream().map(AssetTransactionReadDto::getVolume)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add));

                        shortPosition.setOpenPrice(
                                shorts.stream().map(AssetTransactionReadDto::getOpenPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .divide(new BigDecimal(longs.size()), RoundingMode.UNNECESSARY));

                        var sum = new ArrayList<BigDecimal>();
                        var totalVolume = new ArrayList<BigDecimal>();
                        longs.forEach(current -> {
                            sum.add(current.getOpenPrice().multiply(current.getVolume()));
                            totalVolume.add(current.getVolume());
                        });

                        shortPosition.setOpenPrice(sum.stream().reduce(new BigDecimal(0), BigDecimal::add)
                                .divide(totalVolume.stream().reduce(new BigDecimal(0), BigDecimal::add),
                                        RoundingMode.FLOOR));

                        shortPosition.setProfit(shortPosition.getOpenPrice().subtract(currentPrice)
                                .multiply(shortPosition.getQuantity()));

                        positions.add(shortPosition);
                    }
                }
        );

        return positions;
    }
}
