package com.didenko.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PositionDto {

    private String assetName;

    private String direction;

    private BigDecimal quantity;

    private BigDecimal openPrice;

    private String openDate;

    private BigDecimal currentPrice;

    private BigDecimal profit;

}
