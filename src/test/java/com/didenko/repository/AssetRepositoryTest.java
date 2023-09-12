package com.didenko.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Transactional
class AssetRepositoryTest {

    private final AssetRepository assetRepository;

    @Test
    void getAllByPortfolioId() {

        var assets = assetRepository.getAllByPortfolioId(1L);

        System.out.println();

    }
}