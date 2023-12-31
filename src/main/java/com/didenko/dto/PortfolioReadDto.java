package com.didenko.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PortfolioReadDto {

    private final Long id;

    private final String name;

    private final String description;

    private String assets;

    private BigDecimal profit;
}