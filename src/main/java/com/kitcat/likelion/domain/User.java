package com.kitcat.likelion.domain;

import com.kitcat.likelion.domain.enumration.GrowthStatus;
import com.kitcat.likelion.domain.enumration.RoleType;
import com.kitcat.likelion.domain.enumration.RoutineTerm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
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

    private double bmi;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST)
    private List<Pet> pets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = PERSIST)
    private List<Routine> routines = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Heart> hearts  = new ArrayList<>();

    public User(String email, String nickname, String password, RoleType role, double height, double weight, double bmi) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void addRoutine(Routine routine) {
        this.routines.add(routine);
    }

    public void addHeart(Heart heart) {
        this.hearts.add(heart);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }


}
