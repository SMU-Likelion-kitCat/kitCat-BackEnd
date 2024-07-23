package com.kitcat.likelion.domain;

import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.*;

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

    @Enumerated(STRING)
    private RoutineType routineType;

    @Enumerated(STRING)
    private RoutineTerm routineTerm;
}
