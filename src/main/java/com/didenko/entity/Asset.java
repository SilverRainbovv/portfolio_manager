package com.didenko.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString(exclude = {"portfolio"})
@EqualsAndHashCode(exclude = {"portfolio"})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String name;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    private String comments;

    @Builder.Default
    @OneToMany(mappedBy = "asset", fetch = FetchType.LAZY)
    private List<AssetTransaction> transactions = new ArrayList<>();

    public void addTransaction(AssetTransaction transaction){
        transactions.add(transaction);
    }
}
