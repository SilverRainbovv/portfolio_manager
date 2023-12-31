package com.didenko.repository;

import com.didenko.entity.AssetTransaction;
import com.didenko.entity.TransactionState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long>,
        PagingAndSortingRepository<AssetTransaction, Long> {

    List<AssetTransaction> findAllByAssetId(Long assetId);

    Page<AssetTransaction> findAllByAssetPortfolioId(Long portfolioId, Pageable pageable);
    Page<AssetTransaction> findAllByAssetNameAndAssetPortfolioId(String asset_name, Long asset_portfolio_id, Pageable pageable);
    List<AssetTransaction> findAllByAssetPortfolioId(Long portfolioId);

    @Query("select t from AssetTransaction t " +
            "join t.asset a " +
            "join a.portfolio p " +
            "where t.state = :state and p.id = :portfolioId")
    List<AssetTransaction> findAllByPortfolioIdAndTransactionState(Long portfolioId, TransactionState state);

    @Modifying
    @Query("update AssetTransaction t " +
            "set t.quantity = :volume, t.openPrice = :openPrice, t.openDate = :openDate, " +
            "t.closePrice = :closePrice, t.closeDate = :closeDate, " +
            "t.state = :transactionState " +
            "where t.id = :transactionId")
    void updateById(Long transactionId, BigDecimal volume, BigDecimal openPrice, BigDecimal closePrice,
                    LocalDateTime openDate, LocalDateTime closeDate, TransactionState transactionState);
}
