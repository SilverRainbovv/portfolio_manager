package com.didenko.dto;

import com.didenko.entity.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserCreateEditDto {

    private String email;

    private String password;

    private Role role;

    private String firstname;

    private String lastname;

    private String birthDate;

}
