package com.didenko.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString(exclude = {"userInfo", "portfolios"})
@EqualsAndHashCode(exclude = {"userInfo", "portfolios"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Transient
    @Builder.Default
    private UserPreferences userPreferences = new UserPreferences();

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserInfo userInfo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Portfolio> portfolios;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
