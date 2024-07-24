package com.kitcat.likelion.domain;

import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private String target;

    private String name;

    private String colorCode;

    /**
     * ToDo: 루틴 빈도 만들기
     */

    @Enumerated(STRING)
    private RoutineType routineType;

    @Enumerated(STRING)
    private RoutineTerm routineTerm;

    @ManyToOne(fetch = LAZY)
    private User user;

    public void setUser(User user) {
        this.user = user;

        if(!user.getRoutines().contains(this)) {
            user.addRoutine(this);
        }
    }

    @Builder
    public Routine(String target, String name, String colorCode, RoutineType routineType, RoutineTerm routineTerm) {
        this.target = target;
        this.name = name;
        this.colorCode = colorCode;
        this.routineType = routineType;
        this.routineTerm = routineTerm;
    }
}
