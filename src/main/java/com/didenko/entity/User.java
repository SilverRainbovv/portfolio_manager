package com.didenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString(exclude = {"userInfo", "portfolios"})
@EqualsAndHashCode(exclude = {"userInfo", "portfolios"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserInfo userInfo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Portfolio> portfolios;
}
