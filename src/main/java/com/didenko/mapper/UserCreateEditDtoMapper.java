package com.didenko.mapper;

import com.didenko.dto.UserCreateEditDto;
import com.didenko.entity.Role;
import com.didenko.entity.User;
import com.didenko.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserCreateEditDtoMapper implements Mapper<UserCreateEditDto, User> {

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

        entity.setEmail(object.getEmail());
        entity.setPassword(object.getPassword());
        entity.setRole(Role.USER);
    }
}
