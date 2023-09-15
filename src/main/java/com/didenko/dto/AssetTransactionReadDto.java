package com.didenko.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AssetTransactionReadDto {

    private String positionDirection;

    private BigDecimal quantity;

    private BigDecimal openPrice;

    private BigDecimal closePrice;

    private String openDate;

    private String closeDate;

    private BigDecimal currentPrice;

    private BigDecimal profit;

}