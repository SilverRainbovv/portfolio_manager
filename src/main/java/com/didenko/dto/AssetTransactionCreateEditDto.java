package com.didenko.dto;

import com.didenko.entity.AssetType;
import com.didenko.entity.PositionDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AssetTransactionCreateEditDto {

    private String assetName;

    private AssetType assetType;

    private PositionDirection direction;

    private String openPrice;

    private String volume;

    private LocalDateTime openDate;

    private Long portfolioId;

}
