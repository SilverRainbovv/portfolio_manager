package com.didenko.repository;

import com.didenko.entity.AssetTransaction;
import com.didenko.entity.TransactionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long> {

    List<AssetTransaction> findAllByAssetId(Long assetId);


    @Query("select t from AssetTransaction t " +
            "join t.asset a " +
            "join a.portfolio p " +
            "where p.id = :portfolioId "
    )
    List<AssetTransaction> findAllByPortfolioId(Long portfolioId);

    @Query("select t from AssetTransaction t " +
            "join t.asset a " +
            "join a.portfolio p " +
            "where t.state = :state and p.id = :portfolioId")
    List<AssetTransaction> findAllByPortfolioIdAndTransactionState(Long portfolioId, TransactionState state);
}
