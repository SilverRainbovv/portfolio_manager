package com.didenko.service;

import com.didenko.dto.UserCreateEditDto;
import com.didenko.dto.UserLoginDto;
import com.didenko.dto.UserReadDto;
import com.didenko.entity.User;
import com.didenko.mapper.UserCreateEditDtoMapper;
import com.didenko.mapper.UserReadDtoMapper;
import com.didenko.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadDtoMapper userReadDtoMapper;
    private final UserCreateEditDtoMapper userCreateEditDtoMapper;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

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

    public Optional<UserReadDto> login(UserLoginDto userLoginDto) {
        var maybeUser = userRepository.findByEmail(userLoginDto.getEmail());
        if (maybeUser.isPresent() && maybeUser.get().getPassword().equals(userLoginDto.getPassword())) {
            return Optional.of(userReadDtoMapper.mapFrom(maybeUser.get()));
        }
        return Optional.empty();
    }

    public Optional<UserReadDto> findByEmail(String email) {

        return userRepository.findByEmail(email)
                .map(userReadDtoMapper::mapFrom);
    }
}