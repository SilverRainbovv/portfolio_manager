package com.didenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString(exclude = {"user", "assets"})
@EqualsAndHashCode(exclude = {"user", "assets"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "portfolio")
    @Builder.Default
    private List<Asset> assets = new ArrayList<>();

    public void addAsset(Asset asset){
        assets.add(asset);
    }
}
