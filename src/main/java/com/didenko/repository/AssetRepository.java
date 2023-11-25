package com.didenko.repository;

import com.didenko.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, CrudRepository<Asset, Long> {
    List<Asset> getAllByPortfolioId(Long id);

    Optional<Asset> findByNameAndPortfolioId(String name, Long portfolioId);

}
