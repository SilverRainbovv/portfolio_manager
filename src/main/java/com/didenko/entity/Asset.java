package com.didenko.entity;


import jakarta.persistence.*;
import lombok.*;

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

    private String comments;

    @OneToMany(mappedBy = "asset")
    private List<AssetTransaction> transaction;

}
