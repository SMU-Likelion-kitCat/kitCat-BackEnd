package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DayRoutineDTO {
    @Schema(description = "해당 날의 사용자 산책 기록 배열")
    private List<RoutineDTO> routines = new ArrayList<>();

    public void addRoutine(RoutineDTO routineDTO) {
        routines.add(routineDTO);
    }
}
