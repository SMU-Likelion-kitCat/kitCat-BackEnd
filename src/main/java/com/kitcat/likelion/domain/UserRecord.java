package com.kitcat.likelion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
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

    @ManyToOne(fetch = LAZY)
    private Routine routine;

    @OneToMany(mappedBy = "userRecord", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<PetRecord> petRecords = new ArrayList<>();

    @OneToMany(mappedBy = "userRecord", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<Path> paths = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;

        if(!routine.getUserRecords().contains(this)) {
            routine.addUserRecord(this);
        }
    }

    public void addPetRecord(PetRecord petRecord) {
        this.petRecords.add(petRecord);
    }

    public void addPath(Path path) {
        this.paths.add(path);
    }

    public UserRecord(int calorie, int distance, int walkTime) {
        this.calorie = calorie;
        this.distance = distance;
        this.walkTime = walkTime;
    }
}
