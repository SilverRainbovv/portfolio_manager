package com.didenko.mapper;

import com.didenko.dto.UserReadDto;
import com.didenko.entity.Role;
import com.didenko.entity.User;
import com.didenko.entity.UserInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserReadDtoMapperTest {

    private static final UserInfo USER_INFO = UserInfo.builder()
            .firstname("Jay")
            .lastname("Ray")
            .birthDate(LocalDate.of(1995, 1, 1))
            .build();
    private static final User USER = User.builder()
            .id(1L)
            .email("example@gmail.com")
            .password("randomPasw")
            .role(Role.USER)
            .userInfo(USER_INFO)
            .build();
    private static final UserReadDto EXPECTED = UserReadDto.builder()
            .id(1L)
            .email("example@gmail.com")
            .role(Role.USER.name())
            .firstname("Jay")
            .lastname("Ray")
            .birthDate(LocalDate.of(1995, 1, 1).toString())
            .build();

    private static final UserReadDtoMapper mapper = new UserReadDtoMapper();

    @Test
    void mapFrom() {
        var result = mapper.mapFrom(USER);
        assertEquals(EXPECTED, result);
    }
}