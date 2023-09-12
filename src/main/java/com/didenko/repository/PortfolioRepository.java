package com.didenko.repository;

import com.didenko.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("select p from Portfolio p " +
            "join p.user u " +
            "where u.id = :userId")
    List<Portfolio> findByUserId(Long userId);

}
