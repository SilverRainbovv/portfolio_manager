package com.didenko.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstname;

    private String lastname;

    private LocalDate birthDate;

    public void setUser(User user){
        this.user = user;
        user.setUserInfo(this);
    }

}
