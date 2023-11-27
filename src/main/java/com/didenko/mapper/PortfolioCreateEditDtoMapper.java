package com.didenko.mapper;

import com.didenko.dto.PortfolioCreateEditDto;
import com.didenko.entity.Portfolio;
import org.springframework.stereotype.Component;

@Component
public class PortfolioCreateEditDtoMapper implements Mapper<PortfolioCreateEditDto, Portfolio> {
    @Override
    public Portfolio mapFrom(PortfolioCreateEditDto object) {
        return Portfolio.builder()
                .name(object.getName())
                .description(object.getDescription())
                .user(object.getUser())
                .build();
    }
}