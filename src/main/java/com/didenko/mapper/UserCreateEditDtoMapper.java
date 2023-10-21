package com.didenko.mapper;

import com.didenko.dto.UserCreateEditDto;
import com.didenko.entity.Role;
import com.didenko.entity.User;
import com.didenko.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserCreateEditDtoMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;

    public User mapFrom(UserCreateEditDto object, User entity) {
       copy(object, entity);
       return entity;
    }

    @Override
    public User mapFrom(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);

        return user;
    }

    private void copy(UserCreateEditDto object, User entity){
        UserInfo userInfo = UserInfo.builder()
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .birthDate(LocalDate.parse(object.getBirthDate()))
                .build();
        userInfo.setUser(entity);

        Optional.ofNullable(object.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(entity::setPassword);

        entity.setEmail(object.getEmail());
        entity.setRole(Role.USER);
    }
}
