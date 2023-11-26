package com.didenko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
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
