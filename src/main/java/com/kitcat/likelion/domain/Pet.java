package com.kitcat.likelion.domain;

import com.kitcat.likelion.domain.enumration.GrowthStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@ToString(exclude = {"user"})
@NoArgsConstructor
public class Pet {
    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private double weight;

    @Enumerated(STRING)
    private GrowthStatus growthStatus;

    @ManyToOne(fetch = LAZY)
    private User user;

    public void setUser(User user) {
        this.user = user;

        if(!user.getPets().contains(this)) {
            user.addPet(this);
        }
    }

    public Pet(String name, String image, double weight, GrowthStatus growthStatus) {
        this.name = name;
        this.image = image;
        this.weight = weight;
        this.growthStatus = growthStatus;
    }
}
