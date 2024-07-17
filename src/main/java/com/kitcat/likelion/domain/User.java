package com.kitcat.likelion.domain;

import com.kitcat.likelion.domain.enumration.GrowthStatus;
import com.kitcat.likelion.domain.enumration.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<Pet> pets = new ArrayList<>();

    public User(String email, String nickname, String password, RoleType role, double height, double weight) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.height = height;
        this.weight = weight;
    }
}
