package com.didenko.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserReadDto {

    private final Long id;

    private final String email;

    private final String role;

    private final String firstname;

    private final String lastname;

    private final String birthDate;

}
