package com.didenko.repository;

import com.didenko.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("select p from Portfolio p " +
            "join p.user u " +
            "where u.id = :userId")
    List<Portfolio> findByUserId(Long userId);

    @Query("select p from Portfolio p " +
            "join p.user u " +
            "where p.id = :portfolioId and u.id = :userId")
    Optional<Portfolio> verifyPortfolioBelongsToUserById(Long portfolioId, Long userId);

}
