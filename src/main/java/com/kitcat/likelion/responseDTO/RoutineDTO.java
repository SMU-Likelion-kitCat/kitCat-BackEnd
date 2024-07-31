package com.kitcat.likelion.responseDTO;

import com.kitcat.likelion.domain.enumration.RoutineBase;
import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Builder
@AllArgsConstructor
public class RoutineDTO {
    private Long routineId;

    private int step;

    private int count;

    private int target;

    private String name;

    private String colorCode;

    private String routineBase;

    private String routineType;

    private int routineTerm;

    private int progress;
}
