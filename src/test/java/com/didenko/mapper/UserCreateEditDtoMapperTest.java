package com.didenko.mapper;

import com.didenko.dto.UserCreateEditDto;
import com.didenko.entity.Role;
import com.didenko.entity.User;
import com.didenko.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserCreateEditDtoMapperTest {

    private static final UserCreateEditDto USER_CREATE_EDIT_DTO = UserCreateEditDto.builder()
            .email("example@gmail.com")
            .password("randomPasw")
            .role(Role.USER)
            .firstname("Jay")
            .lastname("Ray")
            .birthDate(LocalDate.of(1995, 1, 1).toString())
            .build();

    private static final UserInfo CONVERTED_USER_INFO = UserInfo.builder()
            .firstname("Jay")
            .lastname("Ray")
            .birthDate(LocalDate.of(1995, 1, 1))
            .build();
    private static final User USER_CONVERTED = User.builder()
            .id(null)
            .email("example@gmail.com")
            .password("randomPasw")
            .role(Role.USER)
            .build();

    @InjectMocks
    @Autowired
    UserCreateEditDtoMapper mapper;

    @Test
    void mapFrom() {
        CONVERTED_USER_INFO.setUser(USER_CONVERTED);
        var result = mapper.mapFrom(USER_CREATE_EDIT_DTO);
        result.setPassword("randomPasw");
        assertEquals(USER_CONVERTED, result);
    }
}