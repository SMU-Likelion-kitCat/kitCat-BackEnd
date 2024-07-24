package com.kitcat.likelion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor
public class UserRecord extends BaseTime {
    @Id
    @Column(name = "user_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int calorie;

    private int distance;

    private int walkTime;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "userRecord")
    private List<PetRecord> petRecords = new ArrayList<>();
}
