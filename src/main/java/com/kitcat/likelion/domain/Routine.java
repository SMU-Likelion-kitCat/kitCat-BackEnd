package com.kitcat.likelion.domain;

import com.kitcat.likelion.domain.enumration.RoutineBase;
import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor
public class Routine extends BaseTime {
    @Id
    @Column(name = "routine_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int step;

    private int count;

    private int target;

    private String name;

    private String colorCode;

    @Enumerated(STRING)
    private RoutineBase routineBase;

    @Enumerated(STRING)
    private RoutineType routineType;

    @Enumerated(STRING)
    private RoutineTerm routineTerm;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "routine",cascade = PERSIST)
    private List<UserRecord> userRecords = new ArrayList<>();

    public void addUserRecord(UserRecord userRecord) {
        this.userRecords.add(userRecord);
    }

    public void setUser(User user) {
        this.user = user;

        if(!user.getRoutines().contains(this)) {
            user.addRoutine(this);
        }
    }

    @Builder
    public Routine(int target, int step, String name, String colorCode, RoutineType routineType, RoutineTerm routineTerm, RoutineBase routineBase, int count) {
        this.step = step;
        this.name = name;
        this.count = count;
        this.target = target;
        this.colorCode = colorCode;
        this.routineType = routineType;
        this.routineTerm = routineTerm;
        this.routineBase = routineBase;
    }
}
