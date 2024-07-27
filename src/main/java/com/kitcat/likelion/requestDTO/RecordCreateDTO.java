package com.kitcat.likelion.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecordCreateDTO {

    @Schema(description = "선택한 루틴 Id(선택하지 않았으면 null)", example = "1")
    private Long routineId;

    @Schema(description = "소모 칼로리", example = "230")
    private int calorie;

    @Schema(description = "이동 거리", example = "3900")
    private int distance;

    @Schema(description = "산책 시간", example = "3620")
    private int walkTime;

    @Schema(description = "선택한 반려견 소모 칼로리 배열")
    private List<PetCalorieDTO> petRecords = new ArrayList<>();
}
