package com.didenko.mapper;

import com.didenko.dto.PortfolioCreateEditDto;
import com.didenko.entity.Portfolio;
import com.didenko.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioCreateEditDtoMapperTest {

    private static final User USER = User.builder()
            .id(1L)
            .build();
    private static final Portfolio EXPECTED_PORTFOLIO = Portfolio.builder()
            .name("portfolio9000")
            .description("mega-portfolio")
            .user(USER)
            .build();

    private static final PortfolioCreateEditDto PORTFOLIO_CREATE_EDIT_DTO = PortfolioCreateEditDto.builder()
            .user(USER)
            .name("portfolio9000")
            .description("mega-portfolio")
            .build();

    private static final PortfolioCreateEditDtoMapper mapper = new PortfolioCreateEditDtoMapper();

    @Test
    void mapFrom() {
        Portfolio result = mapper.mapFrom(PORTFOLIO_CREATE_EDIT_DTO);
        assertEquals(EXPECTED_PORTFOLIO, result);
    }
}