package com.didenko.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "telegram_chat")
public class TelegramUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long chatId;

    private String token;

    public void setUser(User user){
        this.user = user;
        user.setTelegramUserInfo(this);
    }

}
