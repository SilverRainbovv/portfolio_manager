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
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class TransactionsToPositionConverter {

    public List<PositionDto> convert(List<AssetTransactionReadDto> transactions) {

        List<PositionDto> positions = new ArrayList<>();

        Map<String, List<AssetTransactionReadDto>> transactionsByName = transactions.stream()
                .collect(Collectors.groupingBy(AssetTransactionReadDto::getAssetName));

        transactionsByName.keySet().forEach(name -> {

                    var transaction = transactionsByName.get(name).get(0);

                    BigDecimal currentPrice = transaction.getClosePrice() == null
                            ? transaction.getCurrentPrice().setScale(3, RoundingMode.HALF_UP)
                            : transaction.getClosePrice().setScale(3, RoundingMode.HALF_UP);
                    BigDecimal closePrice = transaction.getClosePrice() == null
                            ? null
                            : transaction.getClosePrice().setScale(3, RoundingMode.HALF_UP);
                    String openDate = transaction.getOpenDate();
                    String closeDate = transaction.getCloseDate();

                    List<AssetTransactionReadDto> longs = transactionsByName.values().stream()
                            .flatMap(List::stream)
                            .filter(t -> t.getAssetName().equals(name))
                            .filter(t -> t.getPositionDirection().equals(PositionDirection.LONG))
                            .toList();

                    List<AssetTransactionReadDto> shorts = transactionsByName.values().stream()
                            .flatMap(List::stream)
                            .filter(t -> t.getAssetName().equals(name))
                            .filter(t -> t.getPositionDirection().equals(PositionDirection.SHORT))
                            .toList();

                    if (!longs.isEmpty()) {
                        PositionDto longPosition = new PositionDto();
                        longPosition.setAssetName(name);
                        longPosition.setDirection(PositionDirection.LONG.name());
                        longPosition.setCurrentPrice(currentPrice);
                        longPosition.setOpenDate(openDate);
                        longPosition.setClosePrice(closePrice);
                        longPosition.setCloseDate(closeDate);

                        longPosition.setQuantity(
                                longs.stream().map(AssetTransactionReadDto::getVolume)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .setScale(3, RoundingMode.HALF_UP));

                        var sum = new ArrayList<BigDecimal>();
                        var totalVolume = new ArrayList<BigDecimal>();
                        longs.forEach(current -> {
                            sum.add(current.getOpenPrice().multiply(current.getVolume()));
                            totalVolume.add(current.getVolume());
                        });

                        longPosition.setOpenPrice(sum.stream().reduce(new BigDecimal(0), BigDecimal::add)
                                .divide(totalVolume.stream().reduce(new BigDecimal(0), BigDecimal::add),
                                        RoundingMode.HALF_UP)
                                .setScale(3, RoundingMode.HALF_UP));

                        longPosition.setProfit(currentPrice.subtract(longPosition.getOpenPrice())
                                .multiply(longPosition.getQuantity())
                                .setScale(3, RoundingMode.HALF_UP));

                        positions.add(longPosition);
                    }
                    if (!shorts.isEmpty()) {
                        var shortPosition = new PositionDto();
                        shortPosition.setAssetName(name);
                        shortPosition.setDirection(PositionDirection.SHORT.name());
                        shortPosition.setCurrentPrice(currentPrice);
                        shortPosition.setOpenDate(openDate);
                        shortPosition.setClosePrice(closePrice);
                        shortPosition.setCloseDate(closeDate);

                        shortPosition.setQuantity(
                                shorts.stream().map(AssetTransactionReadDto::getVolume)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .setScale(3, RoundingMode.HALF_UP));

                        var sum = new ArrayList<BigDecimal>();
                        var totalVolume = new ArrayList<BigDecimal>();
                        shorts.forEach(current -> {
                            sum.add(current.getOpenPrice().multiply(current.getVolume()));
                            totalVolume.add(current.getVolume());
                        });

                        shortPosition.setOpenPrice(sum.stream().reduce(new BigDecimal(0), BigDecimal::add)
                                .divide(totalVolume.stream().reduce(new BigDecimal(0), BigDecimal::add),
                                        RoundingMode.HALF_UP)
                                .setScale(3, RoundingMode.HALF_UP));

                        shortPosition.setProfit(shortPosition.getOpenPrice().subtract(currentPrice)
                                .multiply(shortPosition.getQuantity())
                                .setScale(3, RoundingMode.HALF_UP));

                        positions.add(shortPosition);
                    }
                }
        );

        return positions;
    }
}
