package com.didenko.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "portfolio")
    private List<Asset> assets;
}
