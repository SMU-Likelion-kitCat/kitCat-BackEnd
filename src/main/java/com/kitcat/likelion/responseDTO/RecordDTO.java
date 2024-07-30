package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RecordDTO {
    @Schema(description = "산첵 종료 시간")
    private LocalDateTime recordTime;

    @Schema(description = "산첵 시간")
    private int walkTime;
}
