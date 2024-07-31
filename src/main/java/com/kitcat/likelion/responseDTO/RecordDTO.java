package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RecordDTO {
    @Schema(description = "산책 종료 시간")
    private LocalDateTime recordTime;

    @Schema(description = "산책 기록(시간, 칼로리, 거리)")
    private int record;

    @Schema(description = "기록 달성 여부")
    private boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
