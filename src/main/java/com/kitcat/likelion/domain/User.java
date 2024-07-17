package com.kitcat.likelion.domain;

import com.kitcat.likelion.domain.enumrate.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Enumerated(STRING)
    private RoleType role;

    private double height;

    private double weight;

    public User(String email, String nickname, String password, RoleType role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public User(String email, String nickname, String password, RoleType role, double height, double weight) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.height = height;
        this.weight = weight;
    }
}
