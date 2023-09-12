package com.didenko.repository;

import com.didenko.entity.AssetTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long> {

    List<AssetTransaction> findAllByAssetId(Long assetId);

}
