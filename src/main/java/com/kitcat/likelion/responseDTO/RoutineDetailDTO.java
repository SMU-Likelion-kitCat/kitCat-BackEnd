package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class RoutineDetailDTO {
    @Schema(description = "주차별 성공 여부(SUCCESS: 성공 , FAIL: 실패, NONE: 회색) - 배열 크기: 4")
    private List<String> achievement;

    @Schema(description = "주차별 산책 기록(인덱스 0 부터 1주차 기록)")
    private List<WeekRecordDTO> weekRecords;
}
