package com.didenko.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetSumReadDto {

    private String id;

    private String name;

    private String comments;

    private String direction;

    private String longQuantity;

    private String shortQuantity;

    private String longOpenPrice;

    private String shortOpenPrice;

    private String longClosePrice;

    private String shortClosePrice;

}
