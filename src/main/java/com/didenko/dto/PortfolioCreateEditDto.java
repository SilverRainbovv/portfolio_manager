package com.didenko.dto;

import com.didenko.entity.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PortfolioCreateEditDto  {

    private User user;

    private String name;

    private String description;

}
