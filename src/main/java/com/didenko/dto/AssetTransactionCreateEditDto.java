package com.didenko.dto;

import com.didenko.entity.AssetType;
import com.didenko.entity.PositionDirection;
import com.didenko.entity.TransactionState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class AssetTransactionCreateEditDto {

    private Long id;

    private Long portfolioId;

    private String assetName;

    private AssetType assetType;

    private PositionDirection direction;

    private TransactionState transactionState;

    private String openPrice;

    private String volume;

    private LocalDateTime openDate;

    private String closePrice;

    private LocalDateTime closeDate;
}
