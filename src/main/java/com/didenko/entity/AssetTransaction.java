package com.didenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString(exclude = "asset")
@EqualsAndHashCode(exclude = "asset")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class AssetTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    private PositionDirection positionDirection;

    private BigDecimal quantity;

    private BigDecimal openPrice;

    private BigDecimal closePrice;

    private LocalDateTime openDate;

    private LocalDateTime closeDate;

    public void setAsset(Asset asset){
        this.asset = asset;
        asset.addTransaction(this);
    }

}