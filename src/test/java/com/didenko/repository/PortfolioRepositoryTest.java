package com.didenko.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class PortfolioRepositoryTest {

    private final PortfolioRepository portfolioRepository;

    @Test
    void findByUserId() {
        var portfolio = portfolioRepository.findByUserId(1L);
        System.out.println();

    }
}