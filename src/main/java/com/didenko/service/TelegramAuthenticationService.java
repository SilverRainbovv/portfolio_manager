package com.didenko.service;

import com.didenko.entity.TelegramUserInfo;
import com.didenko.entity.User;
import com.didenko.repository.TelegramAuthenticationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class TelegramAuthenticationService {

    private final TelegramAuthenticationRepository telegramRepository;

    public TelegramUserInfo createAuthenticationEntry(User user) {

        var maybeEntry = telegramRepository.findByUserId(user.getId());
        checkIfAuthenticated(maybeEntry);

        var authenticationToken = maybeEntry.isPresent() && maybeEntry.get().getToken() != null
                ? maybeEntry.get().getToken()
                : RandomStringUtils.random(12, true, true);

        return maybeEntry.orElseGet(() -> telegramRepository.save(TelegramUserInfo.builder()
                .user(user)
                .token(authenticationToken)
                .build()));
    }

    private void checkIfAuthenticated(Optional<TelegramUserInfo> maybeEntry) {
        if (maybeEntry.isPresent() && maybeEntry.get().getChatId() != null)
            throw new RuntimeException("You already have authenticated telegram account");
    }

    public String tryAuthorize(String email, String token, Long chatId) {

        var maybeEntry = telegramRepository.findTelegramUserInfoByUser_Email(email);

        if (maybeEntry.isPresent() && maybeEntry.get().getChatId() == null){
            var entry = maybeEntry.get();
            if (entry.getToken().equals(token) && entry.getUser().getEmail().equals(email)){
                entry.setChatId(chatId);
                telegramRepository.updateTelegramUserInfosById(entry.getId(), chatId);
                return "Successful authentication";
            } else {
                return "Bad credentials";
            }
        } else {
            return maybeEntry.isEmpty()
                    ? "Please first generate credentials on your user-page"
                    : "You are already authenticated";
        }
    }
}
