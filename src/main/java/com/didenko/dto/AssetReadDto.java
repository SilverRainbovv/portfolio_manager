package com.didenko.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetReadDto {

    private String id;

    private String name;

    private String assetType;

    private String comments;

    private String direction;

    private BigDecimal longQuantity;

    private BigDecimal shortQuantity;

    private BigDecimal longOpenPrice;

    private BigDecimal shortOpenPrice;

    private BigDecimal currentPrice;

    private BigDecimal longProfit;

    private BigDecimal shortProfit;

}
