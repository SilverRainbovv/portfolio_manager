package com.didenko.repository;

import com.didenko.entity.TelegramUserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramAuthenticationRepository extends CrudRepository<TelegramUserInfo, Long> {

    Optional<TelegramUserInfo> findByUserId(Long userId);

}
