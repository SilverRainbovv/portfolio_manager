package com.didenko.repository;

import com.didenko.entity.Asset;
import com.didenko.entity.TransactionState;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, CrudRepository<Asset, Long> {
    List<Asset> getAllByPortfolioId(Long id);

    Optional<Asset> findByNameAndPortfolioId(String name, Long portfolioId);

}
