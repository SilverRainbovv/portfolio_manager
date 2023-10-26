package com.didenko.repository;

import integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class AssetTransactionRepositoryTest extends IntegrationTestBase {

    private final AssetTransactionRepository repository;

    @Test
    public void checkPageable(){
        var pageable = PageRequest.of(1, 2);

        var result = repository.findAllByAssetPortfolioId(2L);
        var result2 = repository.findAllByPortfolioId(2L, pageable);

         System.out.println();
    }


}