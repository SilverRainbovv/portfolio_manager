package com.didenko.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PositionDto {

    private String assetName;

    private String direction;

    private BigDecimal quantity;

    private BigDecimal openPrice;

    private String openDate;

    private BigDecimal currentPrice;

    private BigDecimal closePrice;

    private String closeDate;

    private BigDecimal profit;

}
