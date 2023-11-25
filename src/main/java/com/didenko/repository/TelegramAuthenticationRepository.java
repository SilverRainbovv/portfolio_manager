package com.didenko.repository;

import com.didenko.entity.TelegramUserInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramAuthenticationRepository extends CrudRepository<TelegramUserInfo, Long> {

    Optional<TelegramUserInfo> findByUserId(Long userId);
    Optional<TelegramUserInfo> findTelegramUserInfoByUser_Email(String email);

    @Modifying
    @Query("update telegram_chat " +
            "set chatId = :chatId " +
            "where id = :id")
    void updateTelegramUserInfosById(Long id, Long chatId);


}
