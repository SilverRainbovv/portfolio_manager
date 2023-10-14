package com.didenko.mapper;

import com.didenko.dto.UserReadDto;
import com.didenko.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadDtoMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto mapFrom(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .email(object.getEmail())
                .role(object.getRole().name())
                .firstname(object.getUserInfo().getFirstname())
                .lastname(object.getUserInfo().getLastname())
                .birthDate(object.getUserInfo().getBirthDate().toString())
                .build();
    }

}
