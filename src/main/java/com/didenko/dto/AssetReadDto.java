package com.didenko.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetReadDto {

    private String id;

    private String name;

    private String comments;

}
