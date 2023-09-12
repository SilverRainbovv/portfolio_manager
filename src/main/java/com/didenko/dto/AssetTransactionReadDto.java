package com.didenko.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetTransactionReadDto {

    private String positionDirection;

    private String quantity;

    private String openPrice;

    private String closePrice;

    private String openDate;

    private String closeDate;


}
