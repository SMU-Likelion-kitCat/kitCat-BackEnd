package com.kitcat.likelion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor
public class PetRecord extends BaseTime {
    @Id
    @Column(name = "pet_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int calorie;

    @ManyToOne(fetch = LAZY)
    private Pet pet;

    @ManyToOne(fetch = LAZY)
    private UserRecord userRecord;
}
