package com.didenko.dto;

import com.didenko.entity.AssetType;
import com.didenko.entity.PositionDirection;
import com.didenko.entity.TransactionState;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AssetTransactionReadDto {

    private Long id;

    private Long portfolioId;

    private AssetType assetType;

    private String assetName;

    private PositionDirection positionDirection;

    private TransactionState state;

    private BigDecimal volume;

    private BigDecimal openPrice;

    private BigDecimal closePrice;

    private String openDate;

    private String closeDate;

    private BigDecimal currentPrice;

    private BigDecimal profit;

}
