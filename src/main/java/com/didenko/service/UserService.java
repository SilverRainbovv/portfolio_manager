package com.didenko.service;

import com.didenko.dto.UserCreateEditDto;
import com.didenko.dto.UserReadDto;
import com.didenko.mapper.UserCreateEditDtoMapper;
import com.didenko.mapper.UserReadDtoMapper;
import com.didenko.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserReadDtoMapper userReadDtoMapper;
    private final UserCreateEditDtoMapper userCreateEditDtoMapper;

    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadDtoMapper::mapFrom);
    }

    public List<UserReadDto> findAll(){
        return userRepository.findAll().stream()
                .map(userReadDtoMapper::mapFrom)
                .toList();
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userCreateEditDto){
        return Optional.of(userRepository
                .save(userCreateEditDtoMapper.mapFrom(userCreateEditDto)))
                .map(userReadDtoMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userCreateEditDto){
        return userRepository.findById(id)
                .map(entity -> userCreateEditDtoMapper.mapFrom(userCreateEditDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadDtoMapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id){
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}