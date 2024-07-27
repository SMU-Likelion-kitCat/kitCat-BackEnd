package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class RoutineRecordDTO {

    @Schema(description = "주차별 산책 기록(인덱스 0 부터 1주차 기록)")
    private List<WeekRecordDTO> weekRecordDTOList;
}
